package no.eirikhs.fktis.kjerne

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

data class AvhengighetPlan<R : Any>(
    val klasse: KClass<R>,
    val avhengigheter: Map<KProperty1<R, *>, Avhengighet<*>>,
)

data class LøstAvhengighet<R : Any?>(
    val avhengighet: Avhengighet<R>,
    val resultat: R,
)
