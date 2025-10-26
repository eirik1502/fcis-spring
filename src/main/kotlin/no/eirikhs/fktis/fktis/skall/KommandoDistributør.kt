package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import kotlin.reflect.KClass

class KommandoDistributør(
    private val behandlere: Collection<KommandoBehandler<*>>,
) {
    private val behandlerVedKommandoType: Map<KClass<out Kommando>, KommandoBehandler<*>> =
        behandlere.associateBy { it.kommandoType }

    fun utfør(kommando: Kommando): Plan {
        val behandler = finnBehandler(kommando)
        return behandler.utfør(kommando)
    }

    fun hentAlleBehandlere(): Set<KommandoBehandler<*>> = behandlere.toSet()

    fun hentAlleKommandoTyper(): Set<KClass<out Kommando>> =
        hentAlleBehandlere()
            .map { it.kommandoType }
            .toSet()

    private fun <K : Kommando> finnBehandler(kommando: K): KommandoBehandler<K> {
        @Suppress("UNCHECKED_CAST")
        return finnBehandler(kommando::class) as KommandoBehandler<K>
    }

    private fun <K : Kommando> finnBehandler(kommandoType: KClass<K>): KommandoBehandler<K> {
        val behandler =
            behandlerVedKommandoType[kommandoType]
                ?: error("Ingen behandler funnet for kommando type: ${kommandoType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return behandler as KommandoBehandler<K>
    }
}
