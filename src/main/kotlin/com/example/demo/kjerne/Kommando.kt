package com.example.demo.kjerne

import com.example.demo.skall.SykmeldingDTO

enum class KommandoType {
    HÅNDTER_SYKMELDING_HENDELSE,
    SYNKRONISER_ARBEIDSFORHOLD,
}

sealed interface Kommando {
    val type: KommandoType

    data class HåndterSykmeldingHendelse(
        val sykmeldingId: String,
        val sykmelding: SykmeldingDTO? = null
    ) : Kommando {
        override val type = KommandoType.HÅNDTER_SYKMELDING_HENDELSE
    }

    data class SynkroniserArbeidsforhold(
        val fnr: String
    ) : Kommando {
        override val type = KommandoType.SYNKRONISER_ARBEIDSFORHOLD
    }
}
