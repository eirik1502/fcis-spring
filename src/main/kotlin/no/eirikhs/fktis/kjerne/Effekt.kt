package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.fktis.kjerne.*
import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.kjerne.bekreftelse.Bekreftelse
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

data class LagreSykmelding(
    val sykmelding: Sykmelding,
) : Effekt

data class SlettSykmelding(
    val sykmeldingId: String,
) : Effekt

data class LagreBekreftelse(
    val bekreftelse: Bekreftelse,
) : Effekt

data class SlettBekreftelse(
    val sykmeldingId: String,
) : Effekt

data class LagreArbeidsforhold(
    val arbeidsforhold: Arbeidsforhold,
) : Effekt

data class SlettArbeidsforhold(
    val fnr: String? = null,
    val databaseId: String? = null,
) : Effekt
