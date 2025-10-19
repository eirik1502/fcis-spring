package com.example.demo.scenarier

import com.example.demo.Testdata
import com.example.demo.aaregArbeidsforhold
import com.example.demo.eksternSykmelding
import com.example.demo.kjerne.arbeidsforhold.Arbeidsforhold
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.skall.porter.ArbeidsforholdRepository
import com.example.demo.skall.porter.SykmeldingHendelseHåndterer
import com.example.demo.skall.porter.SykmeldingRepository
import com.example.demo.sykmelding
import com.example.demo.testoppsett.AaregKlientFake
import com.example.demo.testoppsett.E2eTestOppsett
import com.example.demo.utils.objectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.amshove.kluent.shouldHaveSingleItem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get

class SykmeldingHendelser : E2eTestOppsett() {
    @Autowired
    private lateinit var sykmeldingHendelseHåndterer: SykmeldingHendelseHåndterer

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var aaregKlient: AaregKlientFake

    @Autowired
    private lateinit var sykmeldingRepository: SykmeldingRepository

    @Autowired
    private lateinit var arbeidsforholdRepository: ArbeidsforholdRepository

    @AfterEach
    fun tearDown() {
        aaregKlient.reset()
        sykmeldingRepository.deleteAll()
        arbeidsforholdRepository.deleteAll()
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
