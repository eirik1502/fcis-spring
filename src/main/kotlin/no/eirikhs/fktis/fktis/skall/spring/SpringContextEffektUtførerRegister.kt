package no.eirikhs.fktis.fktis.skall.spring

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.skall.EffektUtfører
import no.eirikhs.fktis.fktis.skall.EffektUtførerRegister
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Component
class SpringContextEffektUtførerRegister(
    private val appContext: ApplicationContext,
) : EffektUtførerRegister {
    override fun hentAlleEffektUtførere(): Set<EffektUtfører<*>> =
        appContext
            .getBeansOfType(EffektUtfører::class.java)
            .values
            .toSet()

    override fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektUtfører<E> {
        val alleUtførere =
            appContext
                .getBeansOfType(EffektUtfører::class.java)
                .values

        @Suppress("UNCHECKED_CAST")
        val utfører: EffektUtfører<Effekt> =
            alleUtførere.firstOrNull { it.effektType.isSubclassOf(effektType) } as EffektUtfører<Effekt>?
                ?: error("Ingen EffektUtfører registrert for type ${effektType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return utfører as EffektUtfører<E>
    }
}
