package no.eirikhs.fktis.fktis.kjerne

import no.eirikhs.fktis.kjerne.UtførKommando

fun byggPlan(bygger: PlanBygger.() -> Unit): Plan = PlanBygger().apply(bygger).bygg()

class PlanBygger {
    private val effekter: MutableList<Effekt> = mutableListOf()

    fun effekt(effekt: Effekt) {
        effekter.add(effekt)
    }

    fun effekter(effekter: Collection<Effekt>) {
        this.effekter.addAll(effekter)
    }

    fun kommando(kommando: Kommando) {
        effekter.add(UtførKommando(kommando = kommando))
    }

    operator fun Effekt.unaryPlus() {
        effekt(this)
    }

    operator fun Collection<Effekt>.unaryPlus() {
        effekter(this)
    }

    operator fun Kommando.unaryPlus() {
        kommando(this)
    }

    fun bygg(): Plan = Plan(effekter)
}
