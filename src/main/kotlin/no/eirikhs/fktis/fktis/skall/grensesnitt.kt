package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import kotlin.reflect.KClass

interface Planlegger<K : Kommando> {
    val kommandoType: KClass<K>

    fun utfør(kommando: K): Plan
}

interface EffektUtfører<E : Effekt> {
    val effektType: KClass<E>

    fun utfør(effekt: E)
}

interface PlanleggerRegister {
    fun hentAllePlanleggere(): Set<Planlegger<*>>

    fun <K : Kommando> finnPlanlegger(kommandoType: KClass<K>): Planlegger<K>

    fun hentAlleKommandoTyper(): Set<KClass<out Kommando>> =
        hentAllePlanleggere()
            .map { it.kommandoType }
            .toSet()

    fun finnPlanlegger(kommando: Kommando): Planlegger<Kommando> {
        @Suppress("UNCHECKED_CAST")
        return finnPlanlegger(kommando::class) as Planlegger<Kommando>
    }
}

inline fun <reified K : Kommando> PlanleggerRegister.finnPlanlegger(): Planlegger<K> = finnPlanlegger(K::class)

interface EffektUtførerRegister {
    fun hentAlleEffektUtførere(): Set<EffektUtfører<*>>

    fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektUtfører<E>

    fun hentAlleEffektTyper(): Set<KClass<out Effekt>> =
        hentAlleEffektUtførere()
            .map { it.effektType }
            .toSet()

    fun finnEffektUtfører(effekt: Effekt): EffektUtfører<Effekt> {
        @Suppress("UNCHECKED_CAST")
        return finnEffektUtfører(effekt::class) as EffektUtfører<Effekt>
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
