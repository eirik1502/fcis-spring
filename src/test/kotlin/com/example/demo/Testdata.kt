package com.example.demo

import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.skall.SykmeldingDTO
import java.time.LocalDate

object Testdata

fun Testdata.sykmeldingDTO(
    sykmeldingId: String = "sykmeldingId",
    fnr: String = "fnr",
    fom: LocalDate = LocalDate.parse("2025-08-01"),
    tom: LocalDate = LocalDate.parse("2025-08-30"),
) = SykmeldingDTO(
    sykmeldingId = sykmeldingId,
    fnr = fnr,
    fom = fom,
    tom = tom,
)

fun Testdata.sykmelding(
    sykmeldingId: String = "sykmeldingId",
    fnr: String = "fnr",
    fom: LocalDate = LocalDate.parse("2025-08-01"),
    tom: LocalDate = LocalDate.parse("2025-08-30"),
) = Sykmelding(
    sykmeldingId = sykmeldingId,
    fnr = fnr,
    fom = fom,
    tom = tom,
)

