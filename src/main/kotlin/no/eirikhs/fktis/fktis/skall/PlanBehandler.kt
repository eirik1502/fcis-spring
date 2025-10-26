package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.*

class PlanBehandler(
    private val effektDistributør: EffektDistributør,
    private val kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
    private val transaksjonBehandler: TransaksjonBehandler,
    private val kommandoLogger: KommandoLogger? = null,
) {
    fun utfør(
        plan: Plan,
        kommando: Kommando? = null,
    ) {
        val ekspandertPlan = ekspander(plan)
        val result =
            kotlin.runCatching {
                utførUtenEkspansjon(ekspandertPlan)
            }
        kommandoLogger?.loggKommandoUtførelse(
            kommando = kommando,
            plan = ekspandertPlan,
            suksess = result.isSuccess,
            feil = result.exceptionOrNull(),
        )
    }

    private fun utførUtenEkspansjon(plan: Plan) {
        utførITransaksjon(plan.transaksjon) {
            for (steg in plan.steg) {
                when (steg) {
                    is Effekt -> effektDistributør.utfør(steg)
                    is Plan -> utførUtenEkspansjon(steg)
                    is UtførKommandoSteg -> error("UtførKommandoSteg skal ikke forekomme i ekspandert plan")
                }
            }
        }
    }

    private fun ekspander(plan: Plan): Plan {
        val ekspanderteEffekter: List<PlanSteg> =
            plan.steg.map { steg ->
                when (steg) {
                    is UtførKommandoSteg ->
                        ekspander(kommandoPlanleggerDistributør.planlegg(steg.kommando))
                    is Plan -> ekspander(steg)
                    is Effekt -> steg
                }
            }
        val ekspandertPlan = plan.copy(steg = ekspanderteEffekter)
        return if (ekspandertPlan != plan) {
            ekspander(ekspandertPlan)
        } else {
            ekspandertPlan
        }
    }

    private fun utførITransaksjon(
        transaksjon: Transaksjon,
        block: () -> Unit,
    ) {
        if (transaksjon == Transaksjon.INGEN) {
            block()
        }
        val propagasjon =
            when (transaksjon) {
                Transaksjon.PÅKREVD -> TransaksjonPropagasjon.PÅKREVD
                Transaksjon.NESTET -> TransaksjonPropagasjon.NESTET
                else -> error("Ukjent transaksjon: $transaksjon")
            }

        transaksjonBehandler.utfør(propagation = propagasjon) {
            block()
        }
    }
}
