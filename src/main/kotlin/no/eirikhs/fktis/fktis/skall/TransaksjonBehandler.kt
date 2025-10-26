package no.eirikhs.fktis.fktis.skall

enum class TransaksjonPropagasjon {
    PÅKREVD,
    NESTET,
}

interface TransaksjonBehandler {
    fun utfør(
        propagation: TransaksjonPropagasjon = TransaksjonPropagasjon.PÅKREVD,
        work: () -> Unit,
    )
}
