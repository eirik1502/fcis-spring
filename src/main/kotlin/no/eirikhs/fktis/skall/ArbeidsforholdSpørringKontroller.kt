package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import no.eirikhs.fktis.skall.porter.ArbeidsforholdRepository
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
