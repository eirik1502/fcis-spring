package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.kjerne.arbeidsforhold.AaregArbeidsforhold
import org.springframework.stereotype.Component

interface AaregKlient {
    fun hentArbeidsforhold(fnr: String): List<AaregArbeidsforhold>
}

@Component("aaregKlient")
class AaregWebKlient : AaregKlient {
    override fun hentArbeidsforhold(fnr: String): List<AaregArbeidsforhold> = emptyList()
}
