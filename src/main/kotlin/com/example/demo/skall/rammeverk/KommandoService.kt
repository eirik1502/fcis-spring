package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.utils.logger
import com.example.demo.utils.objectMapper
import org.springframework.stereotype.Service

interface KommandoService {
    fun utførKommando(kommando: Kommando)

    fun planleggKommando(kommando: Kommando): Plan
}

@Service
class KommandoServiceImpl(
    private val planleggerRegister: PlanleggerRegister,
    private val planUtfører: PlanUtfører,
) : KommandoService {
    private val logger = logger()

    override fun utførKommando(kommando: Kommando) {
        val plan = planleggKommando(kommando)
        planUtfører.utførPlan(plan)
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
