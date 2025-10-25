package no.eirikhs.fktis.fktis.kjerne

data class Plan(
    val effekter: List<Effekt>,
) {
    constructor(vararg effekter: Effekt) : this(effekter = effekter.toList())

    companion object {
        val TOM = Plan()
    }
}
