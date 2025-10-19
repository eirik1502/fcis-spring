package com.example.demo.kjerne

import com.example.demo.kjerne.sykmelding.EksternSykmelding

enum class KommandoType {
    NOOP,
    HÅNDTER_SYKMELDING_HENDELSE,
    SYNKRONISER_ARBEIDSFORHOLD,
}

sealed interface Kommando {
    val type: KommandoType

    data object NoOp : Kommando {
        override val type = KommandoType.NOOP
    }

    data class HåndterSykmeldingHendelse(
        val sykmeldingId: String,
        val sykmelding: EksternSykmelding? = null,
    ) : Kommando {
        override val type = KommandoType.HÅNDTER_SYKMELDING_HENDELSE
    }

    data class SynkroniserArbeidsforhold(
        val fnr: String,
    ) : Kommando {
        override val type = KommandoType.SYNKRONISER_ARBEIDSFORHOLD
    }
}
