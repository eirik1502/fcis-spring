package no.eirikhs.fktis.skall

enum class SykmeldingStatus {
    NY,
    APEN,
    BEKREFTET,
    AVBRUTT,
}

data class SykmeldingRegistrering(
    val databaseId: String? = null,
    val status: SykmeldingStatus = SykmeldingStatus.NY,
)
