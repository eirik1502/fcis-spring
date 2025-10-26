package no.eirikhs.fktis.fktis.skall

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.kjerne.UtførKommando
import no.eirikhs.fktis.skall.config.EFFEKT_JACKON_MODULE
import no.eirikhs.fktis.skall.config.KOMMANDO_JACKSON_MODULE
import no.eirikhs.fktis.utils.logger

class PlanBehandler(
    private val effektDistributør: EffektDistributør,
    private val kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
) {
    private val log = logger()
    private val objectMapper =
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .addModule(KOMMANDO_JACKSON_MODULE)
            .addModule(EFFEKT_JACKON_MODULE)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
            .writerWithDefaultPrettyPrinter()

    fun utfør(plan: Plan) {
        val ekspandertPlan = ekspander(plan)

        if (plan != ekspandertPlan) {
            log.info(
                "Plan ekspandert: " +
                    objectMapper.writeValueAsString(
                        mapOf(
                            "plan" to plan,
                            "ekspandertPlan" to ekspandertPlan,
                        ),
                    ),
            )
        }

        for (effekt in ekspandertPlan.effekter) {
            effektDistributør.utfør(effekt)
        }
    }

    private fun ekspander(plan: Plan): Plan {
        val ekspanderteEffekter =
            plan.effekter.flatMap { effekt ->
                when (effekt) {
                    is UtførKommando -> {
                        kommandoPlanleggerDistributør.planlegg(effekt.kommando).effekter
                    }
                    else -> listOf(effekt)
                }
            }
        return Plan(ekspanderteEffekter)
    }
}
