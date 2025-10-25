package no.eirikhs.fktis.fktis.skall.spring

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.skall.Planlegger
import no.eirikhs.fktis.fktis.skall.PlanleggerRegister
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class SpringContextPlanleggerRegister(
    private val appContext: ApplicationContext,
) : PlanleggerRegister {
    override fun hentAllePlanleggere(): Set<Planlegger<*>> =
        appContext
            .getBeansOfType(Planlegger::class.java)
            .values
            .toSet()

    override fun <K : Kommando> finnPlanlegger(kommandoType: KClass<K>): Planlegger<K> {
        val allePlanleggere =
            appContext
                .getBeansOfType(Planlegger::class.java)
                .values

        @Suppress("UNCHECKED_CAST")
        val planlegger: Planlegger<Kommando> =
            allePlanleggere.firstOrNull { it.kommandoType.isSubclassOf(kommandoType) } as Planlegger<Kommando>?
                ?: error("Ingen Planleggere registrert for kommando type ${kommandoType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return planlegger as Planlegger<K>
    }
}
