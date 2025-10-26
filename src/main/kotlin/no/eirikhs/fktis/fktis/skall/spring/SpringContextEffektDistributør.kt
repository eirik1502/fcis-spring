package no.eirikhs.fktis.fktis.skall.spring

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.skall.EffektBehandler
import no.eirikhs.fktis.fktis.skall.EffektDistributør
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Component
class SpringContextEffektDistributør(
    private val appContext: ApplicationContext,
) : EffektDistributør {
    override fun hentAlleEffektUtførere(): Set<EffektBehandler<*>> =
        appContext
            .getBeansOfType(EffektBehandler::class.java)
            .values
            .toSet()

    override fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektBehandler<E> {
        val alleUtførere =
            appContext
                .getBeansOfType(EffektBehandler::class.java)
                .values

        @Suppress("UNCHECKED_CAST")
        val utfører: EffektBehandler<Effekt> =
            alleUtførere.firstOrNull { it.effektType.isSubclassOf(effektType) } as EffektBehandler<Effekt>?
                ?: error("Ingen EffektUtfører registrert for type ${effektType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return utfører as EffektBehandler<E>
    }
}
