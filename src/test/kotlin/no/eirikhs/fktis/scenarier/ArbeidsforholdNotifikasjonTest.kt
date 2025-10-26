package no.eirikhs.fktis.scenarier

import no.eirikhs.fktis.skall.porter.ArbeidsforholdNotifikasjon
import no.eirikhs.fktis.skall.porter.ArbeidsforholdNotifikasjonLytter
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ArbeidsforholdNotifikasjonTest : E2eTestOppsett() {
    @Autowired
    lateinit var arbeidsforholdNotifikasjonLytter: ArbeidsforholdNotifikasjonLytter

    @Autowired
    lateinit var arbeidsforholdRepository: ArbeidsforholdRepository

    @AfterEach
    fun tearDown() {
        super.resettTilstand()
    }

    @Test
    fun `burde synkronisere alle arbeidsforhold i notifikasjon`() {
        arbeidsforholdNotifikasjonLytter.håndter(
            ArbeidsforholdNotifikasjon(
                fnr = listOf("1", "1", "2", "3"),
            ),
        )
        arbeidsforholdRepository.findAll().shouldHaveSize(3)
    }
}
