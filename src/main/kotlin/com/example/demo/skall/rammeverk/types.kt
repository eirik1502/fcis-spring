package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Effekt
import kotlin.reflect.KClass

class EffektUtfører<E : Effekt>(
    val effektType: KClass<E>,
    val utfør: (E) -> Unit,
)
