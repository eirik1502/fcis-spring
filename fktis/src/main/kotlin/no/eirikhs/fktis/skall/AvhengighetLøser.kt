package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Avhengighet
import kotlin.reflect.KClass

interface AvhengighetLøser<A : Avhengighet<R>, out R> {
    val avhengighetType: KClass<A>

    fun løs(avhengighet: A): R
}
