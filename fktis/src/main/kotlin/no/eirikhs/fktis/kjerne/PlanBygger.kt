package no.eirikhs.fktis.kjerne

@DslMarker
annotation class PlanbyggerDsl

fun byggPlan(
    transaksjon: Transaksjon = Transaksjon.INGEN,
    bygger: PlanBygger.() -> Unit,
): Plan = PlanBygger(transaksjon = transaksjon).apply(bygger).bygg()

@PlanbyggerDsl
class PlanBygger(
    private val transaksjon: Transaksjon = Transaksjon.INGEN,
) {
    private val steg: MutableList<PlanSteg> = mutableListOf()

    fun effekt(effekt: Effekt) {
        steg.add(effekt)
    }

    fun effekter(effekter: Collection<Effekt>) {
        this.steg.addAll(effekter)
    }

    fun kommando(kommando: Kommando) {
        steg.add(UtførKommandoSteg(kommando = kommando))
    }

    fun plan(plan: Plan) {
        steg.add(plan)
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

    operator fun Plan.unaryPlus() {
        plan(this)
    }

    fun bygg(): Plan =
        Plan(
            steg = steg,
            transaksjon = transaksjon,
        )
}
