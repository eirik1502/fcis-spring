package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import kotlin.reflect.KClass

interface KommandoPlanlegger<K : Kommando> {
    val kommandoType: KClass<K>

    fun utfør(kommando: K): Plan
}
