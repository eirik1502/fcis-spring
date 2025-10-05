package com.example.demo.testoppsett

import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import com.example.demo.skall.eksternt.AaregKlient

class AaregFakeKlient : AaregKlient {
    private val arbeidsforhold: MutableMap<String, Result<AaregArbeidsforhold>> = mutableMapOf()

    fun setArbeidsforhold(aaregArbeidsforhold: AaregArbeidsforhold, fnr: String = "__any__") {
        arbeidsforhold[fnr] = Result.success(aaregArbeidsforhold)
    }
    fun setArbeidsforhold(feil: Throwable, fnr: String = "__any__") {
        arbeidsforhold[fnr] = Result.failure(feil)
    }
    fun reset() {
        arbeidsforhold.clear()
    }

    override fun hentArbeidsforhold(fnr: String): AaregArbeidsforhold {
        return arbeidsforhold.getOrElse(fnr) {
            arbeidsforhold.getOrElse("__any__") {
                Result.failure(IllegalStateException("Ingen arbeidsforhold registrert"))
            }
        }.getOrThrow()
    }
}
