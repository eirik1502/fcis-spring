package com.example.demo.skall

import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.skall.porter.SykmeldingRepository
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SykmeldingSpørringKontroller(
    private val sykmeldingRepository: SykmeldingRepository,
) {
    @GetMapping("/api/v1/sykmelding/{sykmeldingId}")
    fun hentSykmelding(
        @PathVariable("sykmeldingId") sykmeldingId: String,
    ): ResponseEntity<Sykmelding> {
        val sykmelding =
            sykmeldingRepository.findBySykmeldingId(sykmeldingId)
        return if (sykmelding != null) {
            ResponseEntity.ok(sykmelding)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
