package no.eirikhs.fktis.kjerne.arbeidsforhold

import no.eirikhs.fktis.fktis.kjerne.byggPlan
import no.eirikhs.fktis.kjerne.LagreArbeidsforhold
import no.eirikhs.fktis.kjerne.SlettArbeidsforhold
import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold

fun synkroniserArbeidsforhold(
    fnr: String,
    aaregArbeidsforhold: Collection<AaregArbeidsforhold> = emptySet(),
    eksisterendeArbeidsforhold: Collection<Arbeidsforhold> = emptySet(),
) = byggPlan {
    val nyeIder = aaregArbeidsforhold.map { it.arbeidsforholdType }.toSet()
    val eksisterendeIder = eksisterendeArbeidsforhold.map { it.arbeidsforholdType }.toSet()
    val opprett =
        (nyeIder - eksisterendeIder).map { nyId ->
            val aaregArbeidsforholdForId = aaregArbeidsforhold.first { it.arbeidsforholdType == nyId }
            aaregArbeidsforholdForId.tilArbeidsforhold(fnr)
        }
    val oppdater =
        (nyeIder intersect eksisterendeIder).map { fellesId ->
            val eksisterende = eksisterendeArbeidsforhold.first { it.arbeidsforholdType == fellesId }
            val aaregArbeidsforholdForId = aaregArbeidsforhold.first { it.arbeidsforholdType == fellesId }
            aaregArbeidsforholdForId.tilArbeidsforhold(fnr).copy(id = eksisterende.id!!)
        }
    val slett =
        (eksisterendeIder - nyeIder).map { eksisterendeId ->
            eksisterendeArbeidsforhold.first { it.arbeidsforholdType == eksisterendeId }
        }
    +slett.map { SlettArbeidsforhold(databaseId = it.id) }
    +opprett.map { LagreArbeidsforhold(arbeidsforhold = it) }
    +oppdater.map { LagreArbeidsforhold(arbeidsforhold = it) }
}

private fun AaregArbeidsforhold.tilArbeidsforhold(fnr: String) =
    Arbeidsforhold(
        navArbeidsforholdId = navArbeidsforholdId,
        fnr = fnr,
        orgnummer = orgnummer,
        juridiskOrgnummer = juridiskOrgnummer,
        orgnavn = orgnavn,
        fom = fom,
        tom = tom,
        arbeidsforholdType = arbeidsforholdType,
    )
