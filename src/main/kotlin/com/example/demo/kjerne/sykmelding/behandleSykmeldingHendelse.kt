package com.example.demo.kjerne.sykmelding

import com.example.demo.kjerne.*

fun behandleSykmeldingHendelse(
    sykmeldingId: String,
    eksternSykmelding: EksternSykmelding? = null,
    eksisterendeSykmelding: Sykmelding? = null,
) = byggPlan {
    when {
        eksternSykmelding != null && eksisterendeSykmelding == null -> {
            +LagreSykmelding(sykmelding = eksternSykmelding.tilSykmelding())
            +Kommando.SynkroniserArbeidsforhold(
                fnr = eksternSykmelding.fnr,
            )
        }
        eksternSykmelding != null && eksisterendeSykmelding != null -> {
            +LagreSykmelding(
                sykmelding =
                    eksternSykmelding
                        .tilSykmelding()
                        .copy(databaseId = eksisterendeSykmelding.databaseId),
            )
        }
        else -> {
            +SlettSykmelding(sykmeldingId = sykmeldingId)
        }
    }
}

internal fun EksternSykmelding.tilSykmelding(): Sykmelding =
    Sykmelding(
        sykmeldingId = this.sykmeldingId,
        fnr = this.fnr,
        fom = this.fom,
        tom = this.tom,
    )
