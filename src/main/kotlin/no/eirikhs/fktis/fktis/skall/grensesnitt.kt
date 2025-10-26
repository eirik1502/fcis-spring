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

interface KommandoDistributør {
    fun hentAlleBehandlere(): Set<KommandoBehandler<*>>

    fun <K : Kommando> finnBehandler(kommandoType: KClass<K>): KommandoBehandler<K>

    fun hentAlleKommandoTyper(): Set<KClass<out Kommando>> =
        hentAlleBehandlere()
            .map { it.kommandoType }
            .toSet()

    fun finnBehandler(kommando: Kommando): KommandoBehandler<Kommando> {
        @Suppress("UNCHECKED_CAST")
        return finnBehandler(kommando::class) as KommandoBehandler<Kommando>
    }
}

inline fun <reified K : Kommando> KommandoDistributør.finnBehandler(): KommandoBehandler<K> = finnBehandler(K::class)

interface EffektDistributør {
    fun hentAlleEffektUtførere(): Set<EffektBehandler<*>>

    fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektBehandler<E>

    fun hentAlleEffektTyper(): Set<KClass<out Effekt>> =
        hentAlleEffektUtførere()
            .map { it.effektType }
            .toSet()

    fun finnEffektUtfører(effekt: Effekt): EffektBehandler<Effekt> {
        @Suppress("UNCHECKED_CAST")
        return finnEffektUtfører(effekt::class) as EffektBehandler<Effekt>
    }
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
