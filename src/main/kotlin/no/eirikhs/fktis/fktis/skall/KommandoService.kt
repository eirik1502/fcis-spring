package no.eirikhs.fktis.fktis.skall

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.config.EFFEKT_JACKON_MODULE
import no.eirikhs.fktis.skall.config.KOMMANDO_JACKSON_MODULE
import no.eirikhs.fktis.utils.logger

interface KommandoService {
    fun utførKommando(
        kommando: Kommando,
        metadata: KommandoMetadata? = null,
    )

    fun planleggKommando(kommando: Kommando): Plan

    fun utførPlan(plan: Plan)
}

data class KommandoMetadata(
    val kildesystem: String? = null,
    val aktørtype: String? = null,
    val aktørident: String? = null,
)

class KommandoServiceImpl(
    private val kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
    private val planBehandler: PlanBehandler,
    private val kommandoLogger: KommandoLogger? = null,
) : KommandoService {
    private val logger = logger()

    private val objectMapper =
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .addModule(KOMMANDO_JACKSON_MODULE)
            .addModule(EFFEKT_JACKON_MODULE)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
            .writerWithDefaultPrettyPrinter()

    override fun utførKommando(
        kommando: Kommando,
        metadata: KommandoMetadata?,
    ) {
        val plan = planleggKommando(kommando)
        val resultat =
            runCatching {
                utførPlan(plan)
            }
        kommandoLogger?.loggKommandoUtførelse(
            kommando = kommando,
            plan = plan,
            suksess = resultat.isSuccess,
            feil = resultat.exceptionOrNull(),
            metadata = metadata,
        )
        logger.info("Utført plan")
    }

    override fun planleggKommando(kommando: Kommando): Plan {
        val plan = kommandoPlanleggerDistributør.planlegg(kommando)
        logger.info(
            "Planlagt kommando: " +
                objectMapper.writeValueAsString(
                    mapOf(
                        "kommando" to kommando,
                        "plan" to plan,
                    ),
                ),
        )
        return plan
    }

    override fun utførPlan(plan: Plan) {
        planBehandler.utfør(plan)
    }
}
