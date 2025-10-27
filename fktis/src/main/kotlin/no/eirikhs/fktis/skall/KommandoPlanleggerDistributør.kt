package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.utils.logger
import kotlin.reflect.KClass

class KommandoPlanleggerDistributør(
    private val behandlere: Collection<KommandoPlanlegger<*>>,
) {
    private val log = logger()
    private val behandlerVedKommandoType: Map<KClass<out Kommando>, KommandoPlanlegger<*>> =
        behandlere.associateBy { it.kommandoType }

    fun planlegg(kommando: Kommando): Plan {
        val behandler = finnBehandler(kommando)
        return behandler.utfør(kommando).also {
            log.debug("Planlagt kommando: {}", mapOf("kommando" to kommando, "plan" to it))
        }
    }

    fun hentAlleBehandlere(): Set<KommandoPlanlegger<*>> = behandlere.toSet()

    private fun <K : Kommando> finnBehandler(kommando: K): KommandoPlanlegger<K> {
        @Suppress("UNCHECKED_CAST")
        return finnBehandler(kommando::class) as KommandoPlanlegger<K>
    }

    private fun <K : Kommando> finnBehandler(kommandoType: KClass<K>): KommandoPlanlegger<K> {
        val behandler =
            behandlerVedKommandoType[kommandoType]
                ?: error("Ingen behandler funnet for kommando type: ${kommandoType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return behandler as KommandoPlanlegger<K>
    }
}
