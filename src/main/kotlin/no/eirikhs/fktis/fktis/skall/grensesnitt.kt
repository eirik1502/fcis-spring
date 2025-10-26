package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import kotlin.reflect.KClass

interface KommandoBehandler<K : Kommando> {
    val kommandoType: KClass<K>

    fun utfør(kommando: K): Plan
}

interface EffektBehandler<E : Effekt> {
    val effektType: KClass<E>

    fun utfør(effekt: E)
}

interface KommandoLogger {
    fun loggKommandoUtførelse(
        kommando: Kommando,
        plan: Plan,
        suksess: Boolean = true,
        feil: Throwable? = null,
        metadata: KommandoMetadata? = null,
    )
}
