package no.eirikhs.fktis.kjerne.arbeidsforhold

import java.time.LocalDate

data class AaregArbeidsforhold(
    val navArbeidsforholdId: String,
    val orgnummer: String,
    val juridiskOrgnummer: String,
    val orgnavn: String,
    val fom: LocalDate,
    val tom: LocalDate? = null,
    val arbeidsforholdType: ArbeidsforholdType?,
)

enum class ArbeidsforholdType {
    FORENKLET_OPPGJOERSORDNING,
    FRILANSER_OPPDRAGSTAKER_HONORAR_PERSONER_MM,
    MARITIMT_ARBEIDSFORHOLD,
    ORDINAERT_ARBEIDSFORHOLD,
    PENSJON_OG_ANDRE_TYPER_YTELSER_UTEN_ANSETTELSESFORHOLD,
}
