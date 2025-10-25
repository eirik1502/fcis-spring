package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.fktis.skall.KommandoService
import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelse
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
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
            HåndterSykmeldingHendelse(
                sykmeldingId = sykmeldingId,
                sykmelding = sykmelding,
            ),
        )
    }
}
