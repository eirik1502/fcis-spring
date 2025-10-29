package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.KommandoTestData
import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.eksternSykmelding
import no.eirikhs.fktis.sykmelding
import no.eirikhs.fktis.test.kjerne.shouldContainEffekt
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HåndterSykmeldingHendelseKommandoTest {
    @Test
    fun `test spørring`() {
        val spørring =
            håndterSykmeldingHendelseSpørring(
                kommando = KommandoTestData.håndterSykmeldingHendelse(sykmeldingId = "id-1"),
            )

        spørring.burdeInneholde(FinnSykmelding(sykmeldingId = "id-1"))
    }

    @Nested
    inner class HåndterSykmeldingHendelse {
        @Test
        fun `burde lagre ny sykmelding`() {
            håndterSykmeldingHendelse(
                sykmeldingId = "id-1",
                eksternSykmelding = Testdata.eksternSykmelding(sykmeldingId = "id-1"),
                eksisterendeSykmelding = null,
            ).shouldContainEffekt<LagreSykmelding>()
        }

        @Test
        fun `burde oppdatere eksisterende sykmelding`() {
            håndterSykmeldingHendelse(
                sykmeldingId = "id-1",
                eksternSykmelding = Testdata.eksternSykmelding(sykmeldingId = "id-1"),
                eksisterendeSykmelding = Testdata.sykmelding(sykmeldingId = "id-1"),
            ).shouldContainEffekt<LagreSykmelding>()
        }
    }

    fun SpørringBeskrivelse.burdeInneholde(spørring: Spørring<*>) {
        if (!this.spørringer.contains(spørring)) {
            throw AssertionError("Forventet at spørringBeskrivelse skulle inneholde spørring: $spørring. Spørringer: ${this.spørringer}")
        }
    }
}
