package com.example.demo.kjerne

import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
import org.amshove.kluent.fail
import org.junit.jupiter.api.Test

class SykmeldingTest {

    @Test
    fun `sletter sykmelding`() {
        val plan = behandleSykmeldingHendelse(
            sykmeldingId = "1",
            sykmelding = null,
            eksisterendeSykmelding = null
        )

        plan.shouldContainEffekt<SlettSykmelding>()
        plan.shouldContainEffekt<SlettSykmeldingRegistreringer>()
    }

    inline fun <reified T> Plan.shouldContainEffekt() {
        val effekt = effekter.find { T::class.isInstance(it) }
        if (effekt == null) {
            fail("Forventet effekt av type ${T::class.simpleName}")
        }
    }

    inline fun <reified T> Plan.shouldNotContainEffekt() {
        val effekt = effekter.find { T::class.isInstance(it) }
        if (effekt != null) {
            fail("Forventet ikke effekt av type ${T::class.simpleName}")
        }
    }

}
