package no.eirikhs.fktis.skall.hjelpere

import no.eirikhs.fktis.kjerne.Effekt
import no.eirikhs.fktis.skall.EffektBehandler
import kotlin.reflect.KClass

fun <E : Effekt> lagEffektBehandler(
    effektType: KClass<E>,
    utfør: (E) -> Unit,
) = object : EffektBehandler<E> {
    override val effektType: KClass<E> = effektType

    override fun utfør(effekt: E) = utfør.invoke(effekt)
}

inline fun <reified E : Effekt> lagEffektBehandler(noinline utfør: (E) -> Unit) = lagEffektBehandler(effektType = E::class, utfør = utfør)
