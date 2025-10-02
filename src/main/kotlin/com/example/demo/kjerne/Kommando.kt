package com.example.demo.kjerne

import com.example.demo.kjerne.sykmelding.SykmeldingDTO

sealed interface Kommando {
    data class HåndterSykmeldingHendelse(
        val sykmeldingId: String,
        val sykmelding: SykmeldingDTO? = null
    ) : Kommando

    data class SynkroniserArbeidsforhold(
        val fnr: String
    ) : Kommando

}
