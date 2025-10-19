package no.eirikhs.fktis.skall.rammeverk

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import kotlin.reflect.KClass

interface Planlegger<K : Kommando> {
    val kommandoType: KClass<K>

    fun utfør(kommando: K): Plan
}

fun <K : Kommando> lagPlanlegger(
    kommandoType: KClass<K>,
    utfør: (K) -> Plan,
) = object : Planlegger<K> {
    override val kommandoType: KClass<K> = kommandoType

    override fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}
