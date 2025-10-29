package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelseKommando
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
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
            HåndterSykmeldingHendelseKommando(
                sykmeldingId = sykmeldingId,
                sykmelding = sykmelding,
            ),
        )
    }
}
