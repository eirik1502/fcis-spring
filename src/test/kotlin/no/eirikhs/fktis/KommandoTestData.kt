package no.eirikhs.fktis

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.isSubclassOf

object KommandoTestData {
    fun noop() = Kommando.NoOp

    fun håndterSykmeldingHendelse(
        sykmeldingId: String = "sykmeldingId",
        sykmelding: EksternSykmelding? = Testdata.eksternSykmelding(),
    ) = Kommando.HåndterSykmeldingHendelse(
        sykmeldingId = sykmeldingId,
        sykmelding = sykmelding,
    )

    fun synkroniserArbeidsforhold(fnr: String = "fnr") =
        Kommando.SynkroniserArbeidsforhold(
            fnr = fnr,
        )

    fun alleKommandoer(): List<Kommando> =
        listOf(
            noop(),
            håndterSykmeldingHendelse(),
            synkroniserArbeidsforhold(),
        )
}
