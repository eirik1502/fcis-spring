package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Effekt
import kotlin.reflect.KClass

interface EffektBehandler<E : Effekt> {
    val effektType: KClass<E>

    fun utfør(effekt: E)
}
