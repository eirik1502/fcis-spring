package com.example.demo.skall

import com.example.demo.core.Domain
import com.example.demo.core.Plan
import com.example.demo.core.SykmeldingDTO
import org.springframework.stereotype.Component

@Component
class BehandleSykmeldingHendelse(
    private val sykmeldingRepository: SykmeldingRepository,
) {
    fun behandle(
        sykmeldingId: String,
        sykmelding: SykmeldingDTO?,
    ): Plan {
        val nySykmelding = sykmelding?.let { konvertSykmelding(it) }
        val eksisterendeSykmelding = sykmeldingRepository.findBySykmeldingId(sykmeldingId)

        return Domain.behandleSykmeldingHendelse(
            sykmeldingId = sykmeldingId,
            sykmelding = nySykmelding,
            eksisterendeSykmelding = eksisterendeSykmelding,
        )
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