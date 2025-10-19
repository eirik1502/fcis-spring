package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.eksternSykmelding
import no.eirikhs.fktis.kjerne.sykmelding.behandleSykmeldingHendelse
import no.eirikhs.fktis.sykmelding
import org.amshove.kluent.fail
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.Test

class SykmeldingTest {
    @Test
    fun `burde lagre ny sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                eksternSykmelding = Testdata.eksternSykmelding(),
                eksisterendeSykmelding = null,
            )

        plan.shouldContainEffekt<no.eirikhs.fktis.kjerne.LagreSykmelding>()
        plan.shouldContainUtførKommandoEffekt<no.eirikhs.fktis.kjerne.Kommando.SynkroniserArbeidsforhold>()
    }

    @Test
    fun `burde oppdatere sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                eksternSykmelding = Testdata.eksternSykmelding(),
                eksisterendeSykmelding = Testdata.sykmelding(),
            )

        plan.shouldContainEffekt<_root_ide_package_.no.eirikhs.fktis.kjerne.LagreSykmelding>()
    }

    @Test
    fun `burde tombstone sykmelding`() {
        val plan =
            behandleSykmeldingHendelse(
                sykmeldingId = "1",
                eksternSykmelding = null,
                eksisterendeSykmelding = null,
            )

        plan.shouldContainEffekt<_root_ide_package_.no.eirikhs.fktis.kjerne.SlettSykmelding>()
    }

    inline fun <reified T> _root_ide_package_.no.eirikhs.fktis.kjerne.Plan.shouldContainEffekt(): T {
        val matchendeEffekter = effekter.filterIsInstance<T>()
        if (matchendeEffekter.isEmpty()) {
            fail("Forventet effekt av type ${T::class.simpleName}")
        }
        return matchendeEffekter.first()
    }

    inline fun <reified K : _root_ide_package_.no.eirikhs.fktis.kjerne.Kommando> _root_ide_package_.no.eirikhs.fktis.kjerne.Plan.shouldContainUtførKommandoEffekt(): K =
        shouldContainEffekt<_root_ide_package_.no.eirikhs.fktis.kjerne.UtførKommando>()
            .kommando
            .shouldBeInstanceOf<K>()

    inline fun <reified T> _root_ide_package_.no.eirikhs.fktis.kjerne.Plan.shouldNotContainEffekt() {
        val effekt = effekter.find { T::class.isInstance(it) }
        if (effekt != null) {
            fail("Forventet ikke effekt av type ${T::class.simpleName}")
        }
    }
}
