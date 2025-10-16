package com.example.demo.kjerne

import com.example.demo.Testdata
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
import com.example.demo.sykmelding
import org.amshove.kluent.fail
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.Test

class SykmeldingTest {
    @Test
    fun `burde lagre ny sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = Testdata.sykmelding(),
                eksisterendeSykmelding = null,
            )

        plan.shouldContainEffekt<LagreSykmelding>()
        plan.shouldContainEffekt<LagreNySykmeldingRegistrering>()
        plan.shouldContainUtførKommandoEffekt<Kommando.SynkroniserArbeidsforhold>()
    }

    @Test
    fun `burde oppdatere sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = Testdata.sykmelding(),
                eksisterendeSykmelding = Testdata.sykmelding(),
            )

        plan.shouldContainEffekt<OppdaterSykmelding>()
    }

    @Test
    fun `burde tombstone sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = null,
                eksisterendeSykmelding = null,
            )

        plan.shouldContainEffekt<SlettSykmelding>()
        plan.shouldContainEffekt<SlettSykmeldingRegistreringer>()
    }

    inline fun <reified T> Plan.shouldContainEffekt(): T {
        val matchendeEffekter = effekter.filterIsInstance<T>()
        if (matchendeEffekter.isEmpty()) {
            fail("Forventet effekt av type ${T::class.simpleName}")
        }
        return matchendeEffekter.first()
    }

    inline fun <reified K : Kommando> Plan.shouldContainUtførKommandoEffekt(): K =
        shouldContainEffekt<UtførKommando>()
            .kommando
            .shouldBeInstanceOf<K>()

    inline fun <reified T> Plan.shouldNotContainEffekt() {
        val effekt = effekter.find { T::class.isInstance(it) }
        if (effekt != null) {
            fail("Forventet ikke effekt av type ${T::class.simpleName}")
        }
    }
}
