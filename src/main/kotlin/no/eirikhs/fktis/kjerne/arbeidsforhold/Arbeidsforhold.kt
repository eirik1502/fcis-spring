package no.eirikhs.fktis.kjerne.arbeidsforhold

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.time.LocalDate

@Table("arbeidsforhold")
data class Arbeidsforhold(
    @Id
    val id: String? = null,
    val navArbeidsforholdId: String,
    val fnr: String,
    val orgnummer: String,
    val juridiskOrgnummer: String,
    val orgnavn: String,
    val fom: LocalDate,
    val tom: LocalDate? = null,
    val arbeidsforholdType: ArbeidsforholdType?,
    val opprettet: Instant = Instant.now(),
)
