package com.example.demo.skall.eksternt

import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import org.springframework.stereotype.Component

interface AaregKlient {
    fun hentArbeidsforhold(fnr: String): AaregArbeidsforhold
}

@Component
class AaregWebKlient : AaregKlient {
    override fun hentArbeidsforhold(fnr: String): AaregArbeidsforhold {
        return AaregArbeidsforhold(
            navArbeidsforholdId = "1"
        )
    }

}
