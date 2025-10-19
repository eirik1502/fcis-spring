package no.eirikhs.fktis.testoppsett

import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.aaregArbeidsforhold
import no.eirikhs.fktis.kjerne.arbeidsforhold.AaregArbeidsforhold
import no.eirikhs.fktis.skall.porter.AaregKlient

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
