package no.eirikhs.fktis.skall

import no.eirikhs.fktis.KommandoTestData
import no.eirikhs.fktis.skall.rammeverk.PlanleggerRegister
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired

class PlanleggerRegisterTest : E2eTestOppsett() {
    @Autowired
    lateinit var register: PlanleggerRegister

    @TestFactory
    fun `burde finne kommando utfører`() =
        KommandoTestData
            .alleKommandoer()
            .map { kommando ->
                DynamicTest.dynamicTest(kommando.type.name) {
                    val utfører = register.finnPlanlegger(kommando)
                    utfører.kommandoType shouldBeEqualTo kommando::class
                }
            }
}
