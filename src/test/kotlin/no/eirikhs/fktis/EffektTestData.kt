package no.eirikhs.fktis

import no.eirikhs.fktis.kjerne.*
import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.kjerne.bekreftelse.Bekreftelse
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

object EffektTestData {
    fun utførKommando(kommando: Kommando = KommandoTestData.noop()) =
        UtførKommando(
            kommando = kommando,
        )

    fun lagreSykmelding(sykmelding: Sykmelding = Testdata.sykmelding()) =
        LagreSykmelding(
            sykmelding = sykmelding,
        )

    fun slettSykmelding(sykmeldingId: String = "sykmeldingId") =
        SlettSykmelding(
            sykmeldingId = sykmeldingId,
        )

    fun lagreBekreftelse(bekreftelse: Bekreftelse = Testdata.bekreftelse()) =
        LagreBekreftelse(
            bekreftelse = bekreftelse,
        )

    fun slettBekreftelse(sykmeldingId: String = "sykmeldingId") =
        SlettBekreftelse(
            sykmeldingId = sykmeldingId,
        )

    fun lagreArbeidsforhold(arbeidsforhold: Arbeidsforhold = Testdata.arbeidsforhold()) =
        LagreArbeidsforhold(
            arbeidsforhold = arbeidsforhold,
        )

    fun slettArbeidsforhold(
        fnr: String? = "fnr",
        databaseId: String? = "databaseId",
    ) = SlettArbeidsforhold(
        fnr = fnr,
        databaseId = databaseId,
    )

    fun alleEffekter(): List<Effekt> =
        listOf(
            utførKommando(),
            lagreSykmelding(),
            slettSykmelding(),
            lagreBekreftelse(),
            slettBekreftelse(),
            lagreArbeidsforhold(),
            slettArbeidsforhold(),
        )
}
