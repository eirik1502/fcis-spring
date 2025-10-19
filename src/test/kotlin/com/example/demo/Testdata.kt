package com.example.demo

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.KommandoType
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import com.example.demo.kjerne.arbeidsforhold.Arbeidsforhold
import com.example.demo.kjerne.arbeidsforhold.ArbeidsforholdType
import com.example.demo.kjerne.sykmelding.EksternSykmelding
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.skall.kommandologg.KommandoLogg
import java.time.Instant
import java.time.LocalDate

object Testdata

fun Testdata.aaregArbeidsforhold(
    navArbeidsforholdId: String = "navArbeidsforholdId",
    orgnummer: String = "orgnummer",
    juridiskOrgnummer: String = "juridiskOrgnummer",
    orgnavn: String = "orgnavn",
    fom: LocalDate = LocalDate.parse("2020-01-01"),
    tom: LocalDate? = null,
    arbeidsforholdType: ArbeidsforholdType? = null,
): AaregArbeidsforhold =
    AaregArbeidsforhold(
        navArbeidsforholdId = navArbeidsforholdId,
        orgnummer = orgnummer,
        juridiskOrgnummer = juridiskOrgnummer,
        orgnavn = orgnavn,
        fom = fom,
        tom = tom,
        arbeidsforholdType = arbeidsforholdType,
    )

fun Testdata.arbeidsforhold(
    id: String? = null,
    navArbeidsforholdId: String = "navArbeidsforholdId",
    fnr: String = "fnr",
    orgnummer: String = "orgnummer",
    juridiskOrgnummer: String = "juridiskOrgnummer",
    orgnavn: String = "orgnavn",
    fom: LocalDate = LocalDate.parse("2020-01-01"),
    tom: LocalDate? = null,
    arbeidsforholdType: ArbeidsforholdType? = null,
    opprettet: Instant = Instant.parse("2020-01-01T00:00:00Z"),
): Arbeidsforhold =
    Arbeidsforhold(
        id = id,
        navArbeidsforholdId = navArbeidsforholdId,
        fnr = fnr,
        orgnummer = orgnummer,
        juridiskOrgnummer = juridiskOrgnummer,
        orgnavn = orgnavn,
        fom = fom,
        tom = tom,
        arbeidsforholdType = arbeidsforholdType,
        opprettet = opprettet,
    )

fun Testdata.eksternSykmelding(
    sykmeldingId: String = "sykmeldingId",
    fnr: String = "fnr",
    fom: LocalDate = LocalDate.parse("2025-08-01"),
    tom: LocalDate = LocalDate.parse("2025-08-30"),
) = EksternSykmelding(
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

fun Testdata.kommandoLogg(
    kommandoLoggId: String? = null,
    opprettet: Instant = Instant.parse("2024-01-01T00:00:00Z"),
    kommandoType: String = KommandoType.NOOP.name,
    kommando: Kommando = Kommando.NoOp,
    plan: Plan = Plan.TOM,
) = KommandoLogg(
    kommandoLoggId = kommandoLoggId,
    opprettet = opprettet,
    kommandoType = kommandoType,
    kommando = kommando,
    plan = plan,
)
