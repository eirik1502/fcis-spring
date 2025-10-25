package no.eirikhs.fktis.fktistest.kjerne

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.kjerne.UtførKommando

inline fun <reified T> Plan.shouldContainEffekt(): T {
    val matchendeEffekter = effekter.filterIsInstance<T>()
    if (matchendeEffekter.isEmpty()) {
        throw AssertionError("Forventet effekt av type ${T::class.simpleName}")
    }
    return matchendeEffekter.first()
}

inline fun <reified K : Kommando> Plan.shouldContainUtførKommandoEffekt(): K {
    val effekt = shouldContainEffekt<UtførKommando>()
    val kommando = effekt.kommando
    assert(kommando is K) {
        "Forventet UtførKommando med kommando av type ${K::class.simpleName}, "
    }
    return kommando as K
}

inline fun <reified T> Plan.shouldNotContainEffekt() {
    val effekt = effekter.find { T::class.isInstance(it) }
    if (effekt != null) {
        throw AssertionError("Forventet ikke effekt av type ${T::class.simpleName}")
    }
}
