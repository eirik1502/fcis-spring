package no.eirikhs.fktis.skall.api

import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArbeidsforholdSpørringKontroller(
    private val arbeidsforholdRepository: ArbeidsforholdRepository,
) {
    @GetMapping("/api/v1/arbeidsforhold")
    fun hentAlleArbeidsforhold(): ResponseEntity<List<Arbeidsforhold>> {
        val arbeidsforhold = arbeidsforholdRepository.findAll().toList()
        return ResponseEntity.ok(arbeidsforhold)
    }
}
