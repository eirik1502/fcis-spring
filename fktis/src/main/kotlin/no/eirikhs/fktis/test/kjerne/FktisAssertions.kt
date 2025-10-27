package no.eirikhs.fktis.test.kjerne

import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.kjerne.UtførKommandoSteg

inline fun <reified T> Plan.shouldContainEffekt(): T {
    val matchendeEffekter = steg.filterIsInstance<T>()
    if (matchendeEffekter.isEmpty()) {
        throw AssertionError("Forventet effekt av type ${T::class.simpleName}")
    }
    return matchendeEffekter.first()
}

inline fun <reified K : no.eirikhs.fktis.kjerne.Kommando> Plan.shouldContainUtførKommandoEffekt(): K {
    val effekt = shouldContainEffekt<UtførKommandoSteg>()
    val kommando = effekt.kommando
    assert(kommando is K) {
        "Forventet UtførKommando med kommando av type ${K::class.simpleName}, "
    }
    return kommando as K
}

inline fun <reified T> Plan.shouldNotContainEffekt() {
    val effekt = steg.find { T::class.isInstance(it) }
    if (effekt != null) {
        throw AssertionError("Forventet ikke effekt av type ${T::class.simpleName}")
    }
}
