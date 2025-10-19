package com.example.demo.skall.porter

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.sykmelding.EksternSykmelding
import com.example.demo.skall.rammeverk.KommandoService
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
