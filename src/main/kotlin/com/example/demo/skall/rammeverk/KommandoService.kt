package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.skall.kommandologg.KommandoLogg
import com.example.demo.skall.kommandologg.KommandoLoggRepository
import com.example.demo.utils.logger
import com.example.demo.utils.objectMapper
import org.springframework.stereotype.Service

interface KommandoService {
    fun utførKommando(
        kommando: Kommando,
        kildesystem: String? = null,
        aktørtype: String? = null,
        aktørident: String? = null,
    )

    fun planleggKommando(kommando: Kommando): Plan
}

@Service
class KommandoServiceImpl(
    private val planleggerRegister: PlanleggerRegister,
    private val planUtfører: PlanUtfører,
    private val kommandoLoggRepository: KommandoLoggRepository,
) : KommandoService {
    private val logger = logger()

    override fun utførKommando(
        kommando: Kommando,
        kildesystem: String?,
        aktørtype: String?,
        aktørident: String?,
    ) {
        val plan = planleggKommando(kommando)
        val resultat =
            runCatching {
                planUtfører.utførPlan(plan)
            }
        kommandoLoggRepository.save(
            KommandoLogg(
                traceId = KommandoTraceContext.getTraceId(),
                kildesystem = kildesystem,
                aktorident = aktørident,
                kommandoType = kommando.type.name,
                kommando = kommando,
                plan = plan,
                suksess = resultat.isSuccess,
                feilmelding = resultat.exceptionOrNull()?.stackTraceToString(),
            ),
        )
        logger.info("Utført plan")
    }

    override fun planleggKommando(kommando: Kommando): Plan {
        val utfører = planleggerRegister.finnPlanlegger(kommando)
        val plan = utfører.utfør(kommando)
        logger.info(
            "Planlagt: " +
                "\n\tkommando: ${objectMapper.writeValueAsString(kommando)}" +
                "\n\tplan: ${objectMapper.writeValueAsString(plan)}",
        )
        return plan
    }
}
