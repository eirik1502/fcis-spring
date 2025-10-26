package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan

interface KommandoLogger {
    fun loggKommandoUtførelse(
        kommando: Kommando?,
        plan: Plan,
        suksess: Boolean = true,
        feil: Throwable? = null,
        metadata: KommandoMetadata? = null,
    )
}
