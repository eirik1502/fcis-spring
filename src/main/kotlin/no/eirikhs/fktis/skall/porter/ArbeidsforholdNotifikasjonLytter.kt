package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.fktis.skall.KommandoService
import no.eirikhs.fktis.kjerne.ArbeidsforholdNotifikasjonKommando
import org.springframework.stereotype.Component

data class ArbeidsforholdNotifikasjon(
    val fnr: List<String>,
)

@Component
class ArbeidsforholdNotifikasjonLytter(
    private val kommandoService: KommandoService,
) {
    fun håndter(notifikasjon: ArbeidsforholdNotifikasjon) {
        notifikasjon.fnr.distinct()
        kommandoService.utførKommando(
            ArbeidsforholdNotifikasjonKommando(fnr = notifikasjon.fnr),
        )
    }
}
