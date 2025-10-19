package com.example.demo.skall.porter

import com.example.demo.kjerne.Kommando
import com.example.demo.skall.SykmeldingDTO
import com.example.demo.skall.rammeverk.KommandoService
import org.springframework.stereotype.Component

@Component
class SykmeldingHendelseHåndterer(
    private val kommandoService: KommandoService,
) {
    fun håndterHendelse(
        sykmeldingId: String,
        sykmelding: SykmeldingDTO?,
    ) {
        kommandoService.utførKommando(
            Kommando.HåndterSykmeldingHendelse(
                sykmeldingId = sykmeldingId,
                sykmelding = sykmelding,
            ),
        )
    }
}
