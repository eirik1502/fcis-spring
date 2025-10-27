package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.eksternSykmelding
import no.eirikhs.fktis.kjerne.sykmelding.behandleSykmeldingHendelse
import no.eirikhs.fktis.sykmelding
import no.eirikhs.fktis.test.kjerne.shouldContainEffekt
import no.eirikhs.fktis.test.kjerne.shouldContainUtførKommandoEffekt
import org.junit.jupiter.api.Test

class SykmeldingTest {
    @Test
    fun `burde lagre ny sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                eksternSykmelding = Testdata.eksternSykmelding(),
                eksisterendeSykmelding = null,
            )

        plan.shouldContainEffekt<LagreSykmelding>()
        plan.shouldContainUtførKommandoEffekt<SynkroniserArbeidsforhold>()
    }

    @Test
    fun `burde oppdatere sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                eksternSykmelding = Testdata.eksternSykmelding(),
                eksisterendeSykmelding = Testdata.sykmelding(),
            )

        plan.shouldContainEffekt<LagreSykmelding>()
    }

    @Test
    fun `burde tombstone sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                eksternSykmelding = null,
                eksisterendeSykmelding = null,
            )

        plan.shouldContainEffekt<SlettSykmelding>()
    }
}
