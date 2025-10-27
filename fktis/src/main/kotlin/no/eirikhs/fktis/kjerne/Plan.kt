package no.eirikhs.fktis.kjerne

data class Plan(
    val steg: List<PlanSteg>,
    val transaksjon: Transaksjon = Transaksjon.INGEN,
) : PlanSteg {
    constructor(vararg steg: PlanSteg) : this(steg = steg.toList())

    companion object {
        val TOM = Plan()
    }
}

sealed interface PlanSteg

enum class Transaksjon {
    INGEN,
    PÅKREVD,
    NESTET,
}
