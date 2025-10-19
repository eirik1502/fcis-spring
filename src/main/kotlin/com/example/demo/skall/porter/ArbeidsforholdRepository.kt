package com.example.demo.skall.porter

import com.example.demo.kjerne.arbeidsforhold.Arbeidsforhold
import org.springframework.data.repository.CrudRepository

interface ArbeidsforholdRepository : CrudRepository<Arbeidsforhold, String> {
    fun findAllByFnr(fnr: String): List<Arbeidsforhold>
}
