package no.eirikhs.fktis.skall

import no.eirikhs.fktis.EffektTestData
import no.eirikhs.fktis.KommandoTestData
import no.eirikhs.fktis.skall.rammeverk.EffektUtførerRegister
import no.eirikhs.fktis.skall.rammeverk.PlanleggerRegister
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired

class EffektUtførerRegisterTest : E2eTestOppsett() {
    @Autowired
    lateinit var register: EffektUtførerRegister

    @TestFactory
    fun `burde finne kommando utfører`() =
        EffektTestData
            .alleEffekter()
            .map { effekt ->
                DynamicTest.dynamicTest(effekt.type.name) {
                    val utfører = register.finnEffektUtfører(effekt)
                    utfører.effektType shouldBeEqualTo effekt::class
                }
            }
}
