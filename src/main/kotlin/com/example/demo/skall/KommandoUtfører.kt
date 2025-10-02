package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.kjerne.sykmelding.SykmeldingDTO
import org.springframework.stereotype.Component
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
@Component
class KommandoUtfører(
    private val sykmeldingRepository: SykmeldingRepository
) {
    fun utfør(kommando: Kommando): Plan {
        return when (kommando) {
            is Kommando.HåndterSykmeldingHendelse -> {
                behandleSykmeldingHendelse(
                    sykmeldingId = kommando.sykmeldingId,
                    sykmelding = kommando.sykmelding?.let { konvertSykmelding(it) },
                    eksisterendeSykmelding = sykmeldingRepository.findBySykmeldingId(kommando.sykmeldingId)
                )
            }
            is Kommando.SynkroniserArbeidsforhold -> {
                TODO()
            }
        }
    }

    internal fun konvertSykmelding(sykmelding: SykmeldingDTO): Sykmelding {
        return Sykmelding(
            sykmeldingId = sykmelding.sykmeldingId,
            fnr = sykmelding.fnr,
            fom = sykmelding.fom,
            tom = sykmelding.tom,
        )
    }
}
