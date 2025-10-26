package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.*
import kotlin.reflect.KClass

fun <K : Kommando> lagKommandoBehandler(
    kommandoType: KClass<K>,
    utfør: (K) -> Plan,
) = object : KommandoBehandler<K> {
    override val kommandoType: KClass<K> = kommandoType

    override fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}

inline fun <reified K : Kommando> lagKommandoBehandler(noinline utfør: (K) -> Plan) =
    lagKommandoBehandler(kommandoType = K::class, utfør = utfør)

fun <E : Effekt> lagEffektBehandler(
    effektType: KClass<E>,
    utfør: (E) -> Unit,
) = object : EffektBehandler<E> {
    override val effektType: KClass<E> = effektType

    override fun utfør(effekt: E) = utfør.invoke(effekt)
}

inline fun <reified E : Effekt> lagEffektBehandler(noinline utfør: (E) -> Unit) = lagEffektBehandler(effektType = E::class, utfør = utfør)
