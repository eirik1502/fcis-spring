package com.example.demo.skall

import com.example.demo.kjerne.arbeidsforhold.Arbeidsforhold
import com.example.demo.skall.porter.ArbeidsforholdRepository
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
