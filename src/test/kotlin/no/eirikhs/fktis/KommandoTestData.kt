package no.eirikhs.fktis

import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelseKommando
import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelseKommandoTest
import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.NoOpKommando
import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding

object KommandoTestData {
    fun noopKommando() = NoOpKommando

    fun håndterSykmeldingHendelse(
        sykmeldingId: String = "sykmeldingId",
        sykmelding: EksternSykmelding? = Testdata.eksternSykmelding(),
    ) = HåndterSykmeldingHendelseKommando(
        sykmeldingId = sykmeldingId,
        sykmelding = sykmelding,
    )

    fun synkroniserArbeidsforhold(fnr: String = "fnr") =
        SynkroniserArbeidsforhold(
            fnr = fnr,
        )

    fun alleKommandoer(): List<Kommando> =
        listOf(
            noopKommando(),
            håndterSykmeldingHendelse(),
            synkroniserArbeidsforhold(),
        )
}
