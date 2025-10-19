package no.eirikhs.fktis.skall.rammeverk

import no.eirikhs.fktis.kjerne.Effekt
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Component
class EffektUtførerRegister(
    private val appContext: ApplicationContext,
) {
    fun finnEffektUtfører(effekt: Effekt): EffektUtfører<Effekt> = finnEffektUtfører(effekt::class)

    @Suppress("UNCHECKED_CAST")
    fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektUtfører<Effekt> {
        val utfører: EffektUtfører<Effekt> =
            appContext
                .getBeansOfType(EffektUtfører::class.java)
                .values
                .firstOrNull { it.effektType.isSubclassOf(effektType) } as EffektUtfører<Effekt>?
                ?: error("Ingen EffektUtfører registrert for type ${effektType.simpleName}")
        return utfører
    }
}
