package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.KommandoBehandler
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
