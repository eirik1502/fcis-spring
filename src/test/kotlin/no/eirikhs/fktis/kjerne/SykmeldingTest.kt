package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.eksternSykmelding
import no.eirikhs.fktis.sykmelding
import no.eirikhs.fktis.test.kjerne.shouldContainEffekt
import no.eirikhs.fktis.test.kjerne.shouldContainUtførKommandoEffekt
import org.junit.jupiter.api.Test

class SykmeldingTest {
    @Test
    fun `burde lagre ny sykmelding`() {
        val plan =
            håndterSykmeldingHendelse(
                HåndterSykmeldingHendelseKommando(
                    sykmeldingId = "1",
                    sykmelding = Testdata.eksternSykmelding(),
                ),
                HåndterSykmeldingHendelseDeps(
                    eksisterendeSykmelding = null,
                ),
            )

        plan.shouldContainEffekt<LagreSykmelding>()
        plan.shouldContainUtførKommandoEffekt<SynkroniserArbeidsforhold>()
    }

    @Test
    fun `burde oppdatere sykmelding`() {
        val plan =
            håndterSykmeldingHendelse(
                HåndterSykmeldingHendelseKommando(
                    sykmeldingId = "1",
                    sykmelding = Testdata.eksternSykmelding(),
                ),
                HåndterSykmeldingHendelseDeps(
                    eksisterendeSykmelding = Testdata.sykmelding(),
                ),
            )

        plan.shouldContainEffekt<LagreSykmelding>()
    }

    @Test
    fun `burde tombstone sykmelding`() {
        val plan =
            håndterSykmeldingHendelse(
                HåndterSykmeldingHendelseKommando(
                    sykmeldingId = "1",
                    sykmelding = null,
                ),
                HåndterSykmeldingHendelseDeps(
                    eksisterendeSykmelding = null,
                ),
            )

        plan.shouldContainEffekt<SlettSykmelding>()
    }
}
