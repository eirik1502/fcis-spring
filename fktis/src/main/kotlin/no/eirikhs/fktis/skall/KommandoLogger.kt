package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan

interface KommandoLogger {
    fun loggKommandoUtførelse(
        kommando: Kommando?,
        plan: Plan,
        suksess: Boolean = true,
        feil: Throwable? = null,
        metadata: KommandoMetadata? = null,
    )
}
