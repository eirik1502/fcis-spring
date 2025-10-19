package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Kommando
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Component
class PlanleggerRegister(
    private val appContext: ApplicationContext,
) {
    @Suppress("UNCHECKED_CAST")
    fun finnPlanlegger(kommando: Kommando): Planlegger<Kommando> = finnPlanlegger(kommando::class) as Planlegger<Kommando>

    final inline fun <reified K : Kommando> finnPlanlegger(): Planlegger<K> = finnPlanlegger(K::class)

    @Suppress("UNCHECKED_CAST")
    fun <K : Kommando> finnPlanlegger(kommandoType: KClass<K>): Planlegger<K> {
        val utfører: Planlegger<Kommando> =
            appContext
                .getBeansOfType(Planlegger::class.java)
                .values
                .firstOrNull { it.kommandoType.isSubclassOf(kommandoType) } as Planlegger<Kommando>?
                ?: error("")
        return utfører as Planlegger<K>
    }
}
