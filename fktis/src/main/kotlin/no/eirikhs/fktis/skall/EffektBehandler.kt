package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Effekt
import kotlin.reflect.KClass

interface EffektBehandler<E : Effekt> {
    val effektType: KClass<E>

    fun utfør(effekt: E)
}
