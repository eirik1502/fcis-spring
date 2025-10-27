package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelse
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
import no.eirikhs.fktis.skall.KommandoService
import org.springframework.stereotype.Component

@Component
class SykmeldingHendelseHåndterer(
    private val kommandoService: no.eirikhs.fktis.skall.KommandoService,
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
