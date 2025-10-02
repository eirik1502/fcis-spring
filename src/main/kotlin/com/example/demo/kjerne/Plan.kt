package com.example.demo.kjerne

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.module.kotlin.addMixIn
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class Plan(
    val effekter: List<Effekt>
) {
    constructor(vararg effekter: Effekt) : this(effekter = effekter.toList())
}

fun byggPlan(bygger: PlanBygger.() -> Unit): Plan {
    return PlanBygger().apply(bygger).bygg()
}

class PlanBygger {
    private val effekter: MutableList<Effekt> = mutableListOf()

    fun effekt(effekt: Effekt) {
        effekter.add(effekt)
    }

    fun kommando(kommando: Kommando) {
        effekter.add(UtførKommando(kommando = kommando))
    }

    operator fun Effekt.unaryPlus() {
        effekt(this)
    }

    operator fun Kommando.unaryPlus() {
        kommando(this)
    }

    fun bygg(): Plan = Plan(effekter)
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
abstract class EffektMixin

private val mapper = jacksonObjectMapper().apply {
    addMixIn<Effekt, EffektMixin>()
}

fun Plan.tilPenTekst(): String {
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
}
