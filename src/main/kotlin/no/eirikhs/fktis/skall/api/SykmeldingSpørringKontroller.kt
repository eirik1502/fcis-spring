package no.eirikhs.fktis.skall.api

import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
