package no.eirikhs.fktis.fktis.kjerne

fun byggPlan(bygger: PlanBygger.() -> Unit): Plan = PlanBygger().apply(bygger).bygg()

class PlanBygger {
    private val effekter: MutableList<PlanSteg> = mutableListOf()

    fun effekt(effekt: Effekt) {
        effekter.add(effekt)
    }

    fun effekter(effekter: Collection<Effekt>) {
        this.effekter.addAll(effekter)
    }

    fun kommando(kommando: Kommando) {
        effekter.add(UtførKommandoSteg(kommando = kommando))
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
