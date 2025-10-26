package no.eirikhs.fktis.kjerne.arbeidsforhold

import no.eirikhs.fktis.fktis.kjerne.byggPlan
import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold

fun håndterArbeidsforholdNotifikasjon(fnr: List<String>) =
    byggPlan {
        fnr.distinct().forEach {
            +SynkroniserArbeidsforhold(fnr = it)
        }
    }
