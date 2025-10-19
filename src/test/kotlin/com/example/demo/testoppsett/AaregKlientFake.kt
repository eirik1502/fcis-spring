package com.example.demo.testoppsett

import com.example.demo.Testdata
import com.example.demo.aaregArbeidsforhold
import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import com.example.demo.skall.porter.AaregKlient

class AaregKlientFake : AaregKlient {
    private val arbeidsforhold: MutableMap<String, Result<List<AaregArbeidsforhold>>> = mutableMapOf()
    private val default = listOf(Testdata.aaregArbeidsforhold())

    fun setArbeidsforhold(
        aaregArbeidsforhold: List<AaregArbeidsforhold>,
        fnr: String = "__any__",
    ) {
        arbeidsforhold[fnr] = Result.success(aaregArbeidsforhold)
    }

    fun setArbeidsforhold(
        feil: Throwable,
        fnr: String = "__any__",
    ) {
        arbeidsforhold[fnr] = Result.failure(feil)
    }

    fun reset() {
        arbeidsforhold.clear()
    }

    override fun hentArbeidsforhold(fnr: String): List<AaregArbeidsforhold> =
        if (arbeidsforhold.isEmpty()) {
            default
        } else {
            arbeidsforhold
                .getOrElse(fnr) {
                    arbeidsforhold.getOrElse("__any__") {
                        Result.failure(IllegalStateException("Ingen arbeidsforhold registrert"))
                    }
                }.getOrThrow()
        }
}
