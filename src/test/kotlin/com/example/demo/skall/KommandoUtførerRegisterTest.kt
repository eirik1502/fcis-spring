package com.example.demo.skall

import com.example.demo.KommandoTestData
import com.example.demo.skall.rammeverk.KommandoUtførerRegister
import com.example.demo.testoppsett.E2eTestOppsett
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired

class KommandoUtførerRegisterTest : E2eTestOppsett() {
    @Autowired
    lateinit var register: KommandoUtførerRegister

    @TestFactory
    fun `burde finne kommando utfører`() =
        KommandoTestData
            .kallAlle()
            .map { kommando ->
                DynamicTest.dynamicTest(kommando.type.name) {
                    val utfører = register.finnKommandoUtfører(kommando)
                    utfører.kommandoType shouldBeEqualTo kommando::class
                }
            }
}
