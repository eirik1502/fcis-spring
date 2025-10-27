package no.eirikhs.fktis.kjerne.arbeidsforhold

import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.byggPlan

fun håndterArbeidsforholdNotifikasjon(fnr: List<String>) =
    byggPlan {
        fnr.distinct().forEach {
            +SynkroniserArbeidsforhold(fnr = it)
        }
    }
