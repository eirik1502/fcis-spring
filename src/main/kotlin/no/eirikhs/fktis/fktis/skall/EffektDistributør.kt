package no.eirikhs.fktis.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Effekt
import kotlin.reflect.KClass

class EffektDistributør(
    private val behandlere: Collection<EffektBehandler<*>>,
) {
    private val behandlerVedEffektType: Map<KClass<out Effekt>, EffektBehandler<*>> =
        behandlere.associateBy { it.effektType }

    fun utfør(effekt: Effekt) {
        val behandler = finnBehandler(effekt)
        behandler.utfør(effekt)
    }

    fun hentAlleBehandlere(): Set<EffektBehandler<*>> = behandlere.toSet()

    fun hentAlleEffektTyper(): Set<KClass<out Effekt>> =
        hentAlleBehandlere()
            .map { it.effektType }
            .toSet()

    private fun <E : Effekt> finnBehandler(effekt: E): EffektBehandler<E> {
        @Suppress("UNCHECKED_CAST")
        return finnBehandler(effekt::class) as EffektBehandler<E>
    }

    private fun <E : Effekt> finnBehandler(effektType: KClass<E>): EffektBehandler<E> {
        val behandler =
            behandlerVedEffektType[effektType]
                ?: throw IllegalArgumentException("Ingen effektbehandler funnet for effekt: ${effektType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return behandler as EffektBehandler<E>
    }
}
