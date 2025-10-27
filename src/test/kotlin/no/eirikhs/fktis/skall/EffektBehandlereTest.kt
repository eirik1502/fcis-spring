package no.eirikhs.fktis.skall

import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.kjerne.LagreSykmelding
import no.eirikhs.fktis.kjerne.SlettSykmelding
import no.eirikhs.fktis.sykmelding
import no.eirikhs.fktis.testoppsett.SykmeldingRepositoryFake
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EffektBehandlereTest {
    val effektBehandlerConfig = EffektBehandlerConfig()

    @Nested
    inner class SlettSykmeldingEffektBehandler {
        val sykmeldingRepository = SykmeldingRepositoryFake()

        val slettSykmeldingEffektBehandler =
            effektBehandlerConfig.slettSykmeldingEffektBehandler(
                sykmeldingRepository = sykmeldingRepository,
            )

        @Test
        fun `burde slette sykmelding`() {
            sykmeldingRepository.save(Testdata.sykmelding(sykmeldingId = "id-1"))

            slettSykmeldingEffektBehandler.utfør(
                SlettSykmelding(sykmeldingId = "id-1"),
            )
            sykmeldingRepository.findBySykmeldingId("id-1").shouldBeNull()
        }

        @Test
        fun `burde godta at sykmelding ikke finnes`() {
            slettSykmeldingEffektBehandler.utfør(
                SlettSykmelding(sykmeldingId = "id-1"),
            )
        }
    }

    @Nested
    inner class LagreSykmeldingEffektBehandler {
        val sykmeldingRepository = SykmeldingRepositoryFake()

        val lagreSykmeldingEffektBehandler =
            effektBehandlerConfig.lagreSykmeldingEffektBehandler(
                sykmeldingRepository = sykmeldingRepository,
            )

        @Test
        fun `burde opprette sykmelding`() {
            lagreSykmeldingEffektBehandler.utfør(
                LagreSykmelding(sykmelding = Testdata.sykmelding(sykmeldingId = "id-1")),
            )
            sykmeldingRepository.findBySykmeldingId("id-1").shouldNotBeNull()
        }

        @Test
        fun `burde oppdatere sykmelding`() {
            val lagretSykmelding = sykmeldingRepository.save(Testdata.sykmelding(sykmeldingId = "id-1"))
            lagreSykmeldingEffektBehandler.utfør(
                LagreSykmelding(sykmelding = lagretSykmelding.copy(sykmeldingId = "id-2")),
            )

            sykmeldingRepository.findBySykmeldingId("id-1").shouldBeNull()
            sykmeldingRepository.findBySykmeldingId("id-2").shouldNotBeNull()
        }
    }
}
