package no.eirikhs.fktis.fktis.kjerne

data class Plan(
    val steg: List<PlanSteg>,
    val unitOfWork: UnitOfWork = UnitOfWork.INGEN,
) : PlanSteg {
    constructor(vararg steg: PlanSteg) : this(steg = steg.toList())

    companion object {
        val TOM = Plan()
    }
}

enum class UnitOfWork {
    INGEN,
    PÅKREVD,
    NESTET,
}

interface PlanSteg
