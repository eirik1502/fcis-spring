package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.utils.logger
import no.eirikhs.fktis.utils.objectMapper

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
    private val kommandoDistributør: KommandoDistributør,
    private val effektDistributør: EffektDistributør,
    private val kommandoLogger: KommandoLogger? = null,
) : KommandoService {
    private val logger = logger()

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
        val plan = kommandoDistributør.utfør(kommando)
        logger.info(
            "Planlagt kommandp: $kommando" +
                "\n\tkommando: ${objectMapper.writeValueAsString(kommando)}" +
                "\n\tplan: ${objectMapper.writeValueAsString(plan)}",
        )
        return plan
    }

    override fun utførPlan(plan: Plan) {
        for (effekt in plan.effekter) {
            effektDistributør.utfør(effekt)
        }
    }
}
