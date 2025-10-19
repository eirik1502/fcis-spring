package no.eirikhs.fktis.skall.rammeverk

import no.eirikhs.fktis.kjerne.Effekt
import kotlin.reflect.KClass

interface EffektUtfører<E : Effekt> {
    val effektType: KClass<E>

    fun utfør(effekt: E)
}

fun <E : Effekt> lagEffektUtfører(
    effektType: KClass<E>,
    utfør: (E) -> Unit,
) = object : EffektUtfører<E> {
    override val effektType: KClass<E> = effektType

    override fun utfør(effekt: E) = utfør.invoke(effekt)
}

inline fun <reified E : Effekt> lagEffektUtfører(noinline utfør: (E) -> Unit) = lagEffektUtfører(effektType = E::class, utfør = utfør)
