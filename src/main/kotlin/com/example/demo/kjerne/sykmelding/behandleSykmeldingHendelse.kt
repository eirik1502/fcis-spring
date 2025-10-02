package com.example.demo.kjerne.sykmelding

import com.example.demo.kjerne.*
import com.example.demo.skall.SykmeldingRegistrering
import com.example.demo.skall.SykmeldingStatus

fun behandleSykmeldingHendelse(
    sykmeldingId: String,
    sykmelding: Sykmelding? = null,
    eksisterendeSykmelding: Sykmelding? = null
) = byggPlan {
    when {
        sykmelding != null && eksisterendeSykmelding == null -> {
            +LagreNySykmelding(sykmelding = sykmelding)
            +LagreNySykmeldingRegistrering(
                registrering = SykmeldingRegistrering(status = SykmeldingStatus.APEN)
            )
            +Kommando.SynkroniserArbeidsforhold(
                fnr = sykmelding.fnr
            )
        }
        sykmelding != null && eksisterendeSykmelding != null -> {
            +OppdaterSykmelding(sykmeldingId = sykmeldingId, sykmelding = sykmelding)
        }
        else -> {
            +SlettSykmelding(sykmeldingId = sykmeldingId)
            +SlettSykmeldingRegistreringer(sykmeldingId = sykmeldingId)
        }
    }
}
