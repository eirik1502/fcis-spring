package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.AvhengighetPlan
import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import kotlin.reflect.KClass

data class RegistrertKommando<K : Kommando, D : Any>(
    val navn: String,
    val kommandoType: KClass<K>,
    val avhengighetType: KClass<D>,
    val avhengigheter: (kommando: K) -> AvhengighetPlan<D>,
    val planlegger: (kommando: K, avhengigheter: D) -> Plan,
) {
    fun planlegg(
        kommando: Kommando,
        avhengigheter: Any,
    ): Plan {
        require(kommandoType.isInstance(kommando))
        require(avhengighetType.isInstance(avhengigheter))
        @Suppress("UNCHECKED_CAST")
        return planlegger(kommando as K, avhengigheter as D)
    }
}

class KommandoRegister(
    kommandoer: Collection<RegistrertKommando<Kommando, *>>,
) {
    constructor(vararg kommando: RegistrertKommando<Kommando, *>) : this(kommando.toList())

    init {
        sjekkDuplikateNavn(kommandoer)
        sjekkDuplikateTyper(kommandoer)
    }

    private val kommandoVedType = kommandoer.associateBy { it.kommandoType }

    fun harKommando(type: KClass<out Kommando>): Boolean = type in kommandoVedType

    fun harKommando(kommando: Kommando): Boolean = harKommando(kommando::class)

    fun finnKommando(type: KClass<out Kommando>): RegistrertKommando<Kommando, *> =
        kommandoVedType[type]
            ?: error("KommandoRegister har ikke kommando for type: $type")

    fun finnKommando(kommando: Kommando): RegistrertKommando<Kommando, *> = finnKommando(kommando::class)

    private fun sjekkDuplikateNavn(kommandoer: Collection<RegistrertKommando<*, *>>) {
        val navn = kommandoer.map { it.navn }
        check(navn == navn.toSet()) {
            val duplikateNavn = navn.groupBy { it }.filter { it.value.size > 1 }.keys
            "KommandoRegister inneholder duplikate kommando navn: $duplikateNavn"
        }
    }

    private fun sjekkDuplikateTyper(kommandoer: Collection<RegistrertKommando<*, *>>) {
        val typer = kommandoer.map { it.kommandoType }
        check(typer == typer.toSet()) {
            val duplikateTyper = typer.groupBy { it }.filter { it.value.size > 1 }.keys
            "KommandoRegister inneholder duplikate kommando typer: $duplikateTyper"
        }
    }
}
