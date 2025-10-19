package no.eirikhs.fktis.kjerne.bekreftelse

data class BrukerSvar(
    val spørsmål: List<SporsmalSvar>,
)

enum class SpørsmålTag {
    BRUKER_ARBEIDSSITUASJON,
    ARBEIDSGIVER_ORGNUMMER,
    FISKER_LOTT_OG_HYRE,
    FISKER_BLAD,
}

data class SporsmalSvar(
    val tag: SpørsmålTag,
    val spørsmål: String,
    val svar: String?,
    val undersporsmal: List<SporsmalSvar>,
)
