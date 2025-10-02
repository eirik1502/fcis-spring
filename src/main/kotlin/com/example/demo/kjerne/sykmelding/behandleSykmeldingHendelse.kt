package com.example.demo.kjerne.sykmelding

import com.example.demo.kjerne.*
import com.example.demo.skall.SykmeldingRegistrering
import com.example.demo.skall.SykmeldingStatus

fun behandleSykmeldingHendelse(
    sykmeldingId: String,
    sykmelding: Sykmelding? = null,
    eksisterendeSykmelding: Sykmelding? = null
): Plan {
    return if (sykmelding == null) {
        Plan(
            SlettSykmelding(sykmeldingId = sykmeldingId),
            SlettSykmeldingRegistreringer(sykmeldingId = sykmeldingId),
        )
    } else if (eksisterendeSykmelding == null) {
        Plan(
            OppdaterSykmelding(sykmeldingId = sykmeldingId, sykmelding = sykmelding),
        )
    } else {
        Plan(
            LagreNySykmelding(sykmelding = sykmelding),
            LagreNySykmeldingRegistrering(
                registrering = SykmeldingRegistrering(status = SykmeldingStatus.APEN)
            ),
            UtførKommando(
                kommando = Kommando.SynkroniserArbeidsforhold(
                    fnr = eksisterendeSykmelding.fnr
                )
            )
        )
    }
}
