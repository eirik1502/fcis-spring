package no.eirikhs.fktis

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.KommandoType
import org.amshove.kluent.shouldBeIn
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class KommandoTestDataTest {
    private val listedeKommandoKlasser = KommandoTestData.alleKommandoer().map { it::class }.toSet()
    private val listedeKommandoTyper = KommandoTestData.alleKommandoer().map { it.type }.toSet()

    @TestFactory
    fun `alle kommando klasser burde være listet i test data`() =
        Kommando::class.sealedSubclasses.map { kommandoKlasse ->
            DynamicTest.dynamicTest("(${kommandoKlasse.simpleName}) ${kommandoKlasse.qualifiedName}") {
                kommandoKlasse shouldBeIn listedeKommandoKlasser
            }
        }

    @TestFactory
    fun `alle kommando typer burde være listet i test data`() =
        KommandoType.entries.map { kommandoType ->
            DynamicTest.dynamicTest(kommandoType.name) {
                kommandoType shouldBeIn listedeKommandoTyper
            }
        }
}
