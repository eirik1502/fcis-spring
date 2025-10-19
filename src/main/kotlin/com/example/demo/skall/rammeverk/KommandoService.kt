package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.utils.logger
import org.springframework.stereotype.Service

interface KommandoService {
    fun utførKommando(kommando: Kommando)

    fun planleggKommando(kommando: Kommando): Plan
}

@Service
class KommandoServiceImpl(
    private val kommandoUtførerRegister: KommandoUtførerRegister,
    private val planUtfører: PlanUtfører,
) : KommandoService {
    private val logger = logger()

    override fun utførKommando(kommando: Kommando) {
        val plan = planleggKommando(kommando)
        planUtfører.utførPlan(plan)
        logger.info("Utført plan" + mapOf("kommando" to kommando.type))
    }

    override fun planleggKommando(kommando: Kommando): Plan {
        val utfører = kommandoUtførerRegister.finnKommandoUtfører(kommando)
        logger.info("Fant utfører: " + mapOf("kommando" to kommando.type, "utfører" to utfører))
        val plan = utfører.utfør(kommando)
        logger.info("Laget plan: " + mapOf("kommando" to kommando.type, "plan" to plan))
        return plan
    }
}
