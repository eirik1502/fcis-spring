package com.example.demo.kjerne

import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.skall.SykmeldingRegistrering

sealed interface Effekt {
}

data class UtførKommando(
    val kommando: Kommando,
) : Effekt
data class LagreNySykmelding(
    val sykmelding: Sykmelding,
) : Effekt
data class OppdaterSykmelding(
    val sykmeldingId: String,
    val sykmelding: Sykmelding,
) : Effekt
data class SlettSykmelding(
    val sykmeldingId: String,
) : Effekt
data class LagreNySykmeldingRegistrering(
    val registrering: SykmeldingRegistrering
) : Effekt
data class SlettSykmeldingRegistreringer(
    val sykmeldingId: String,
) : Effekt
