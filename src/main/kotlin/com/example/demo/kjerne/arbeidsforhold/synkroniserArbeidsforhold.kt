package com.example.demo.kjerne.arbeidsforhold

import com.example.demo.kjerne.SlettSykmelding
import com.example.demo.kjerne.SlettSykmeldingRegistreringer
import com.example.demo.kjerne.byggPlan

fun synkroniserArbeidsforhold(
    fnr: String,
    aaregArbeidsforhold: AaregArbeidsforhold
) = byggPlan {
    +SlettSykmelding(sykmeldingId = "1")
}
