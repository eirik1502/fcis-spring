package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.kjerne.ArbeidsforholdNotifikasjonKommando
import no.eirikhs.fktis.skall.KommandoService
import org.springframework.stereotype.Component

data class ArbeidsforholdNotifikasjon(
    val fnr: List<String>,
)

@Component
class ArbeidsforholdNotifikasjonLytter(
    private val kommandoService: no.eirikhs.fktis.skall.KommandoService,
) {
    fun håndter(notifikasjon: ArbeidsforholdNotifikasjon) {
        notifikasjon.fnr.distinct()
        kommandoService.utførKommando(
            ArbeidsforholdNotifikasjonKommando(fnr = notifikasjon.fnr),
        )
    }
}
