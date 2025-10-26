package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.KommandoPlanlegger
import kotlin.reflect.KClass

fun <K : Kommando> lagKommandoPlanlegger(
    kommandoType: KClass<K>,
    utfør: (K) -> Plan,
) = object : KommandoPlanlegger<K> {
    override val kommandoType: KClass<K> = kommandoType

    override fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}

inline fun <reified K : Kommando> lagKommandoPlanlegger(noinline utfør: (K) -> Plan) =
    lagKommandoPlanlegger(kommandoType = K::class, utfør = utfør)
