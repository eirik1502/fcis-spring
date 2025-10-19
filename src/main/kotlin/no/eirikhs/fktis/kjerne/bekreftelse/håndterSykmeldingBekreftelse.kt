package no.eirikhs.fktis.kjerne.bekreftelse

import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

fun håndterSykmeldingBekreftelse(
    sykmelding: Sykmelding,
    brukerSvar: BrukerSvar? = null,
) {
}

// fun avgjørArbeidssituasjon(
//    sykmelding: Sykmelding,
//    brukerSvar: BrukerSvar,
// ): Arbeidssituasjon {
//    val brukerArbeidssituasjon = brukerSvar.finnSporsmålMedTag(SpørsmålTag.BRUKER_ARBEIDSSITUASJON)?.svar
//    if (brukerArbeidssituasjon == null) {
//        return UkjentArbeidssituasjon()
//    }
//
//    return when (brukerArbeidssituasjon) {
//        "ARBEIDSTAKER" -> {
//            val lottOgHyreSvar = brukerSvar.finnSporsmålMedTag(SpørsmålTag.FISKER_LOTT_OG_HYRE)
//            if (lottOgHyreSvar != null) {
//                val bladSvar = brukerSvar.finnSporsmålMedTag(SpørsmålTag.FISKER_BLAD).påkrevd().svar
//                val blad = when (bladSvar) {
//                    "A" -> FiskerBlad.A
//                    "B" -> FiskerBlad.B
//                    else -> throw IllegalArgumentException("Ukjent fisker blad")
//                }
//                val lottOgHyre = when (lottOgHyreSvar.svar) {
//                    "HYRE" -> "HYRE"
//                    "LOTT" -> "LOTT"
//                    else -> throw IllegalArgumentException("Ukjent lott og hyre svar")
//                }
//                if (lottOgHyre == "HYRE") {
//                    val orgnummer = brukerSvar.finnArbeidsgiverOrgnummer()
//                    return ArbeidstakerFiskerHyre(
//                        arbeidsgiver = Arbeidsgiver(orgnummer = orgnummer),
//                        blad = blad,
//                    )
//                } else if (lottOgHyre == "LOTT") {
//                    return VanligNaringsdrivende
//                }
//            }
//            Arbeidstaker(
//
//            )
//            val orgnummer = brukerSvar.finnArbeidsgiverOrgnummer()
//            Arbeidssituasjon.ARBEIDSTAKER.copy(orgnummer = orgnummer)
//        }
//
//        "SELVSTENDIG_NÆRINGSDRIVENDE" -> Arbeidssituasjon.SELVSTENDIG_NÆRINGSDRIVENDE
//        "FRILANSER" -> Arbeidssituasjon.FRILANSER
//        "INGEN_ARBEIDSFORHOLD" -> Arbeidssituasjon.INGEN_ARBEIDSFORHOLD
//        else -> throw IllegalArgumentException("Ukjent arbeidssituasjon")
//    }
// })
//
// private fun BrukerSvar.finnArbeidsgiverOrgnummer(): String {
//    finnSporsmålMedTag(SpørsmålTag.ARBEIDSGIVER_ORGNUMMER)
//
// }
//
// private fun BrukerSvar.finnSporsmålMedTag(tag: SpørsmålTag): SporsmalSvar? =
//    spørsmål
//        .flatMap { it.undersporsmal }
//        .firstOrNull { it.tag == tag }
//
// private fun SporsmalSvar?.påkrevd(): SporsmalSvar =
//    this
//        ?: throw IllegalArgumentException("Påkrevd spørsmål/svar er null")
