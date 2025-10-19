package com.example.demo

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.sykmelding.EksternSykmelding
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.isSubclassOf

object KommandoTestData {
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

    fun kallAlle(): List<Kommando> {
        val funcs = KommandoTestData::class.functions
        val kommandoGeneratorer =
            funcs.filter {
                it.returnType.classifier is KClass<*> &&
                    (it.returnType.classifier as KClass<*>).isSubclassOf(Kommando::class)
            }
        check(kommandoGeneratorer.isNotEmpty()) {
            "Fant ingen kommando-generatorer i ${KommandoTestData::class.simpleName}"
        }
        return kommandoGeneratorer
            .map { func ->
                try {
                    func.callBy(mapOf(func.instanceParameter!! to this))
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException(
                        "Feil ved generering av test for ${func.name}. Sjekk at alle parametere har default-verdier.",
                        e,
                    )
                }
            }.filterIsInstance<Kommando>()
    }
}
