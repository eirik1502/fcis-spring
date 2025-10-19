package com.example.demo.skall.porter

import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import org.springframework.stereotype.Component

interface AaregKlient {
    fun hentArbeidsforhold(fnr: String): List<AaregArbeidsforhold>
}

@Component("aaregKlient")
class AaregWebKlient : AaregKlient {
    override fun hentArbeidsforhold(fnr: String): List<AaregArbeidsforhold> = emptyList()
}
