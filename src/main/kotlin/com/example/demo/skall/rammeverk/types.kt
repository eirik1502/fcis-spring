package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Effekt
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import kotlin.reflect.KClass

class EffektUtfører<E : Effekt>(
    val effektType: KClass<E>,
    val utfør: (E) -> Unit,
)

data class Planlegger<K : Kommando>(
    val kommandoType: KClass<K>,
    private val utfør: (K) -> Plan,
) {
    fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}
