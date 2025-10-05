package com.example.demo.skall

import com.example.demo.kjerne.Effekt
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.SlettSykmelding
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Component
class PlanUtfører(
    private val appContext: ApplicationContext
) {
    fun utførPlan(plan: Plan) {
        for (effekt in plan.effekter) {
            utførEffekt(effekt)
        }
    }

    internal fun utførEffekt(effekt: Effekt) {
        val utfører = finnEffektUtfører(Effekt::class)
        return utfører.utfør(effekt)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektUtførerBeskrivelse<Effekt> {
        val utfører: EffektUtførerBeskrivelse<Effekt> = appContext.getBeansOfType(EffektUtførerBeskrivelse::class.java)
            .values
            .firstOrNull { it.effektType.isSubclassOf(effektType) } as EffektUtførerBeskrivelse<Effekt>?
            ?: error("")
        return utfører
    }
}

class EffektUtførerBeskrivelse<E : Effekt>(
    val effektType: KClass<E>,
    val utfør: (E) -> Unit
)


@Configuration
class EffektUtførerConfig {
    @Bean
    fun slettSykmeldingEffektUtfører(
        sykmeldingRepository: SykmeldingRepository
    ) = EffektUtførerBeskrivelse(SlettSykmelding::class) { effekt ->
        sykmeldingRepository.findBySykmeldingId(effekt.sykmeldingId)?.let {
            sykmeldingRepository.delete(it)
        }
    }
}
