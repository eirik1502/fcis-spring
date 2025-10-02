package com.example.demo.core

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.amshove.kluent.fail
import org.junit.jupiter.api.Test

class DomainTest {

    @Test
    fun `sletter sykmelding`() {
        val plan = Domain.behandleSykmeldingHendelse(
            sykmeldingId = "1",
            sykmelding = null,
            eksisterendeSykmelding = null
        )

        plan.shouldContainEffekt<SlettSykmelding>()
        plan.shouldContainEffekt<SlettSykmeldingRegistreringer>()
        plan.shouldNotContainEffekt<LagreNySykmelding>()
        plan.shouldNotContainEffekt<LagreNySykmeldingRegistrering>()

        println(prettyPrintPlan(plan))
    }

    private fun prettyPrintPlan(plan: Plan) {
        val json = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(plan)
        println(json)
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