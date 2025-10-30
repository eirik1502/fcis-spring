package no.eirikhs.fktis.skall.hjelpere

import no.eirikhs.fktis.kjerne.Avhengighet
import no.eirikhs.fktis.skall.AvhengighetLøser
import kotlin.reflect.KClass

inline fun <reified A : Avhengighet<R>, R> lagAvhengighetLøser(crossinline løser: (avhengighet: A) -> R) =
    object : AvhengighetLøser<A, R> {
        override val avhengighetType = A::class

        override fun løs(avhengighet: A): R = løser.invoke(avhengighet)
    }
