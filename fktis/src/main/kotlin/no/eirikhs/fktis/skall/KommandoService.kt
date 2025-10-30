package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.AvhengighetPlan
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
    private val kommandoRegister: KommandoRegister,
    private val avhengighetPlanLøser: AvhengighetPlanLøser,
    private val planBehandler: PlanBehandler,
) : KommandoService {
    override fun utførKommando(
        kommando: Kommando,
        metadata: KommandoMetadata?,
    ) {
        val plan = planleggKommando(kommando)
        utførPlan(plan, kommando = kommando)
    }

    fun planleggAvhengigheter(kommando: Kommando): AvhengighetPlan<*> {
        val registrertKommando = kommandoRegister.finnKommando(kommando)
        val plan = registrertKommando.avhengigheter(kommando)
        return plan
    }

    fun hentAvhengigheter(kommando: Kommando): Any {
        val registrertKommando = kommandoRegister.finnKommando(kommando)
        val avhengighetPlan = registrertKommando.avhengigheter(kommando)
        val avhengigheter = avhengighetPlanLøser.løs(avhengighetPlan)
        return avhengigheter
    }

    override fun planleggKommando(kommando: Kommando): Plan {
        val avhengigheter = hentAvhengigheter(kommando)
        val registrertKommando = kommandoRegister.finnKommando(kommando)
        val plan = registrertKommando.planlegg(kommando, avhengigheter)
        return plan
    }

    override fun utførPlan(
        plan: Plan,
        kommando: Kommando?,
    ) {
        planBehandler.utfør(plan, kommando = kommando)
    }
}
