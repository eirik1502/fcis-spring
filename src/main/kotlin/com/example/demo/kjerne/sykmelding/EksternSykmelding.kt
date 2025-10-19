package com.example.demo.kjerne.sykmelding

import java.time.LocalDate

data class EksternSykmelding(
    val sykmeldingId: String,
    val fnr: String,
    val fom: LocalDate,
    val tom: LocalDate,
)
