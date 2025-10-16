package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Component
class KommandoUtførerRegister(
    private val appContext: ApplicationContext,
) {
    @Suppress("UNCHECKED_CAST")
    fun finnKommandoUtfører(kommando: Kommando): KommandoUtfører<Kommando> =
        finnKommandoUtfører(kommando::class) as KommandoUtfører<Kommando>

    final inline fun <reified K : Kommando> finnKommandoUtfører(): KommandoUtfører<K> = finnKommandoUtfører(K::class)

    @Suppress("UNCHECKED_CAST")
    fun <K : Kommando> finnKommandoUtfører(kommandoType: KClass<K>): KommandoUtfører<K> {
        val utfører: KommandoUtfører<Kommando> =
            appContext
                .getBeansOfType(KommandoUtfører::class.java)
                .values
                .firstOrNull { it.kommandoType.isSubclassOf(kommandoType) } as KommandoUtfører<Kommando>?
                ?: error("")
        return utfører as KommandoUtfører<K>
    }
}
