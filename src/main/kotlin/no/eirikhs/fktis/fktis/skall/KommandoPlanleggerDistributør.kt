package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import kotlin.reflect.KClass

class KommandoPlanleggerDistributør(
    private val behandlere: Collection<KommandoPlanlegger<*>>,
) {
    private val behandlerVedKommandoType: Map<KClass<out Kommando>, KommandoPlanlegger<*>> =
        behandlere.associateBy { it.kommandoType }

    fun planlegg(kommando: Kommando): Plan {
        val behandler = finnBehandler(kommando)
        return behandler.utfør(kommando)
    }

    fun hentAlleBehandlere(): Set<KommandoPlanlegger<*>> = behandlere.toSet()

    fun hentAlleKommandoTyper(): Set<KClass<out Kommando>> =
        hentAlleBehandlere()
            .map { it.kommandoType }
            .toSet()

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
