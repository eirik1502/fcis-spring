package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import no.eirikhs.fktis.skall.SykmeldingRegistrering

enum class EffektType {
    UTFØR_KOMMANDO,
    LAGRE_SYKMELDING,
    SLETT_SYKMELDING,
    LAGRE_SYKMELDING_REGISTRERING,
    SLETT_SYKMELDING_REGISTRERINGER,
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

data class LagreSykmeldingRegistrering(
    val registrering: SykmeldingRegistrering,
) : Effekt {
    override val type = EffektType.LAGRE_SYKMELDING_REGISTRERING
}

data class SlettSykmeldingRegistreringer(
    val sykmeldingId: String,
) : Effekt {
    override val type = EffektType.SLETT_SYKMELDING_REGISTRERINGER
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
