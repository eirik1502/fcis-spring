package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
import no.eirikhs.fktis.skall.rammeverk.KommandoService
import org.springframework.stereotype.Component

@Component
class SykmeldingHendelseHåndterer(
    private val kommandoService: KommandoService,
) {
    fun håndterHendelse(
        sykmeldingId: String,
        sykmelding: EksternSykmelding?,
    ) {
        kommandoService.utførKommando(
            Kommando.HåndterSykmeldingHendelse(
                sykmeldingId = sykmeldingId,
                sykmelding = sykmelding,
            ),
        )
    }
}
