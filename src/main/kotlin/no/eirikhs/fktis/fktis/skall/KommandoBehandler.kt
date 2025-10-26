package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import kotlin.reflect.KClass

interface KommandoBehandler<K : Kommando> {
    val kommandoType: KClass<K>

    fun utfør(kommando: K): Plan
}
