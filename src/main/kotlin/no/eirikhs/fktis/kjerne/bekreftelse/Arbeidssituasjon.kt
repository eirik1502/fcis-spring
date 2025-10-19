package no.eirikhs.fktis.kjerne.bekreftelse

enum class ArbeidssituasjonType {
    ARBEIDSTAKER,
    FRILANSER,
    SELVSTENDIG_NAERINGSDRIVENDE,
    KOMBINERT,
    UKJENT,
}

enum class ArbeidstakerType {
    VANLIG,
    FISKER_HYRE,
}

enum class NaringsdrivendeType {
    VANLIG,
    FISKER_LOTT,
}

interface Arbeidssituasjon {
    val arbeidssituasjonType: ArbeidssituasjonType
}

interface Arbeidstaker : Arbeidssituasjon {
    override val arbeidssituasjonType: ArbeidssituasjonType
        get() = ArbeidssituasjonType.ARBEIDSTAKER
    val arbeidstakerType: ArbeidstakerType
    val arbeidsgiver: Arbeidsgiver
}

interface Naringsdrivende : Arbeidssituasjon {
    override val arbeidssituasjonType: ArbeidssituasjonType
        get() = ArbeidssituasjonType.SELVSTENDIG_NAERINGSDRIVENDE
    val naringsdrivendeType: NaringsdrivendeType
}

data class VanligArbeidstaker(
    override val arbeidsgiver: Arbeidsgiver,
) : Arbeidstaker {
    override val arbeidssituasjonType: ArbeidssituasjonType = ArbeidssituasjonType.ARBEIDSTAKER
    override val arbeidstakerType: ArbeidstakerType = ArbeidstakerType.FISKER_HYRE
}

data class ArbeidstakerFiskerHyre(
    override val arbeidsgiver: Arbeidsgiver,
    val blad: FiskerBlad,
) : Arbeidstaker {
    override val arbeidssituasjonType: ArbeidssituasjonType = ArbeidssituasjonType.ARBEIDSTAKER
    override val arbeidstakerType: ArbeidstakerType = ArbeidstakerType.FISKER_HYRE
}

data object VanligNaringsdrivende : Naringsdrivende {
    override val arbeidssituasjonType: ArbeidssituasjonType = ArbeidssituasjonType.SELVSTENDIG_NAERINGSDRIVENDE
    override val naringsdrivendeType: NaringsdrivendeType = NaringsdrivendeType.VANLIG
}

data class KombinertArbeidssituasjon(
    val arbeidssituasjoner: List<Arbeidssituasjon>,
) : Arbeidssituasjon {
    override val arbeidssituasjonType: ArbeidssituasjonType = ArbeidssituasjonType.KOMBINERT
}

data class UkjentArbeidssituasjon(
    val antattArbeidssituasjon: ArbeidssituasjonType? = null,
) : Arbeidssituasjon {
    override val arbeidssituasjonType: ArbeidssituasjonType = ArbeidssituasjonType.UKJENT
}

data class Arbeidsgiver(
    val orgnummer: String,
    val orgnavn: String,
)

enum class FiskerBlad {
    A,
    B,
}
