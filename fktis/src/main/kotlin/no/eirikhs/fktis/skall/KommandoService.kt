package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan

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
