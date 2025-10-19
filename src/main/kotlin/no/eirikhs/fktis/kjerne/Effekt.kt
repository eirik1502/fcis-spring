package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.kjerne.bekreftelse.Bekreftelse
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

enum class EffektType {
    UTFØR_KOMMANDO,
    LAGRE_SYKMELDING,
    SLETT_SYKMELDING,
    LAGRE_BEKREFTELSE,
    SLETT_BEKREFTELSE,
    LAGRE_ARBEIDSFORHOLD,
    SLETT_ARBEIDSFORHOLD,
}

sealed interface Effekt {
    val type: EffektType
}

data class UtførKommando(
    val kommando: Kommando,
) : Effekt {
    override val type = EffektType.UTFØR_KOMMANDO
}

data class LagreSykmelding(
    val sykmelding: Sykmelding,
) : Effekt {
    override val type = EffektType.LAGRE_SYKMELDING
}

data class SlettSykmelding(
    val sykmeldingId: String,
) : Effekt {
    override val type = EffektType.SLETT_SYKMELDING
}

data class LagreBekreftelse(
    val bekreftelse: Bekreftelse,
) : Effekt {
    override val type = EffektType.LAGRE_BEKREFTELSE
}

data class SlettBekreftelse(
    val sykmeldingId: String,
) : Effekt {
    override val type = EffektType.SLETT_BEKREFTELSE
}

data class LagreArbeidsforhold(
    val arbeidsforhold: Arbeidsforhold,
) : Effekt {
    override val type = EffektType.LAGRE_ARBEIDSFORHOLD
}

data class SlettArbeidsforhold(
    val fnr: String? = null,
    val databaseId: String? = null,
) : Effekt {
    override val type = EffektType.SLETT_ARBEIDSFORHOLD
}
