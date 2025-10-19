package com.example.demo.kjerne

import com.example.demo.kjerne.arbeidsforhold.Arbeidsforhold
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.skall.SykmeldingRegistrering

sealed interface Effekt

data class UtførKommando(
    val kommando: Kommando,
) : Effekt

data class LagreSykmelding(
    val sykmelding: Sykmelding,
) : Effekt

data class SlettSykmelding(
    val sykmeldingId: String,
) : Effekt

data class LagreSykmeldingRegistrering(
    val registrering: SykmeldingRegistrering,
) : Effekt

data class SlettSykmeldingRegistreringer(
    val sykmeldingId: String,
) : Effekt

data class LagreArbeidsforhold(
    val arbeidsforhold: Arbeidsforhold,
) : Effekt

data class SlettArbeidsforhold(
    val fnr: String? = null,
    val databaseId: String? = null,
) : Effekt
