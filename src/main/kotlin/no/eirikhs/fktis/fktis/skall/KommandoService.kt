package no.eirikhs.fktis.fktis.skall

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import no.eirikhs.fktis.fktis.kjerne.*
import no.eirikhs.fktis.skall.config.EFFEKT_JACKON_MODULE
import no.eirikhs.fktis.skall.config.KOMMANDO_JACKSON_MODULE
import no.eirikhs.fktis.utils.logger

interface KommandoService {
    fun utførKommando(
        kommando: Kommando,
        metadata: KommandoMetadata? = null,
    )

    fun planleggKommando(kommando: Kommando): Plan

    fun utførPlan(
        plan: Plan,
        kommando: Kommando? = null,
    )
}

data class KommandoMetadata(
    val kildesystem: String? = null,
    val aktørtype: String? = null,
    val aktørident: String? = null,
)

class KommandoServiceImpl(
    private val kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
    private val planBehandler: PlanBehandler,
) : KommandoService {
    override fun utførKommando(
        kommando: Kommando,
        metadata: KommandoMetadata?,
    ) {
        val plan = planleggKommando(kommando)
        utførPlan(plan, kommando = kommando)
    }

    override fun planleggKommando(kommando: Kommando): Plan {
        val plan = kommandoPlanleggerDistributør.planlegg(kommando)
        return plan
    }

    override fun utførPlan(
        plan: Plan,
        kommando: Kommando?,
    ) {
        planBehandler.utfør(plan, kommando = kommando)
    }
}
