package no.eirikhs.fktis.fktis.kjerne

data class Plan(
    val effekter: List<PlanSteg>,
) {
    constructor(vararg effekter: PlanSteg) : this(effekter = effekter.toList())

    companion object {
        val TOM = Plan()
    }
}

interface PlanSteg
