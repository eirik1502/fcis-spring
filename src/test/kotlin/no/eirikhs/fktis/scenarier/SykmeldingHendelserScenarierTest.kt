package no.eirikhs.fktis.scenarier

import com.fasterxml.jackson.module.kotlin.readValue
import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.aaregArbeidsforhold
import no.eirikhs.fktis.eksternSykmelding
import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import no.eirikhs.fktis.skall.porter.SykmeldingHendelseHåndterer
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import no.eirikhs.fktis.sykmelding
import no.eirikhs.fktis.testoppsett.AaregKlientFake
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
import no.eirikhs.fktis.utils.objectMapper
import org.amshove.kluent.shouldHaveSingleItem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get

class SykmeldingHendelserScenarierTest : E2eTestOppsett() {
    @Autowired
    private lateinit var sykmeldingHendelseHåndterer: SykmeldingHendelseHåndterer

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var aaregKlient: AaregKlientFake

    @Autowired
    private lateinit var sykmeldingRepository: SykmeldingRepository

    @Autowired
    private lateinit var kommandoLoggRepository: KommandoLoggRepository

    @AfterEach
    fun tearDown() {
        super.resettTilstand()
    }

    @Test
    fun `ny sykmelding`() {
        aaregKlient.setArbeidsforhold(
            fnr = "1",
            aaregArbeidsforhold = listOf(Testdata.aaregArbeidsforhold()),
        )

        sykmeldingHendelseHåndterer.håndterHendelse(
            sykmeldingId = "1",
            sykmelding =
                Testdata.eksternSykmelding(
                    sykmeldingId = "1",
                    fnr = "1",
                ),
        )

        mockMvc
            .get("/api/v1/sykmelding/1")
            .andExpect { status { isOk() } }
            .andReturn()
            .jsonContent<Sykmelding>()

        mockMvc
            .get("/api/v1/arbeidsforhold")
            .andExpect { status { isOk() } }
            .andReturn()
            .jsonContent<List<Arbeidsforhold>>()
            .shouldHaveSingleItem()
    }

    @Test
    fun `slett sykmelding`() {
        sykmeldingRepository.save(
            Testdata.sykmelding(
                sykmeldingId = "1",
                fnr = "1",
            ),
        )

        sykmeldingHendelseHåndterer.håndterHendelse(
            sykmeldingId = "1",
            sykmelding = null,
        )

        mockMvc
            .get("/api/v1/sykmelding/1")
            .andExpect { status { isNotFound() } }
    }
}

private inline fun <reified R : Any> MvcResult.jsonContent(): R = objectMapper.readValue(response.contentAsString)
