package no.eirikhs.fktis.kjerne

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class AvhengighetPlanBygger<R : Any>(
    private val klasse: KClass<R>,
) {
    private val avhengigheter = mutableMapOf<KProperty1<R, *>, Avhengighet<*>>()

    fun <V : Any?> bind(
        prop: KProperty1<R, V>,
        avhengighet: Avhengighet<V>,
    ) {
        avhengigheter[prop] = avhengighet
    }

    fun <V> bind(binding: Pair<KProperty1<R, V>, Avhengighet<V>>) {
        bind(binding.first, binding.second)
    }

    operator fun <V> Pair<KProperty1<R, V>, Avhengighet<V>>.unaryPlus() {
        bind(this)
    }

    fun bygg(): AvhengighetPlan<R> =
        AvhengighetPlan(
            klasse = klasse,
            avhengigheter = avhengigheter,
        )
}

inline fun <reified R : Any> byggAvhengigheter(block: AvhengighetPlanBygger<R>.() -> Unit): AvhengighetPlan<R> {
    val builder = AvhengighetPlanBygger(R::class)
    block(builder)
    return builder.bygg()
}
