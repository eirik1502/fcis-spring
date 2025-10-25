package no.eirikhs.fktis.skall.repositories

import no.eirikhs.fktis.kjerne.arbeidsforhold.Arbeidsforhold
import org.springframework.data.repository.CrudRepository

interface ArbeidsforholdRepository : CrudRepository<Arbeidsforhold, String> {
    fun findAllByFnr(fnr: String): List<Arbeidsforhold>
}
