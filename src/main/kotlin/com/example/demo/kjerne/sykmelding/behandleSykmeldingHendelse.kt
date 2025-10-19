package com.example.demo.kjerne.sykmelding

import com.example.demo.kjerne.*

fun behandleSykmeldingHendelse(
    sykmeldingId: String,
    sykmelding: Sykmelding? = null,
    eksisterendeSykmelding: Sykmelding? = null,
) = byggPlan {
    when {
        sykmelding != null && eksisterendeSykmelding == null -> {
            +LagreSykmelding(sykmelding = sykmelding)
            +Kommando.SynkroniserArbeidsforhold(
                fnr = sykmelding.fnr,
            )
        }
        sykmelding != null && eksisterendeSykmelding != null -> {
            +LagreSykmelding(sykmelding = sykmelding.copy(databaseId = eksisterendeSykmelding.databaseId))
        }
        else -> {
            +SlettSykmelding(sykmeldingId = sykmeldingId)
        }
    }
}
