package no.eirikhs.fktis.kjerne.sykmelding

import no.eirikhs.fktis.kjerne.*
import no.eirikhs.fktis.kjerne.LagreSykmelding
import no.eirikhs.fktis.kjerne.SlettSykmelding
import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold

fun behandleSykmeldingHendelse(
    sykmeldingId: String,
    eksternSykmelding: EksternSykmelding? = null,
    eksisterendeSykmelding: Sykmelding? = null,
) = byggPlan {
    when {
        eksternSykmelding != null && eksisterendeSykmelding == null -> {
            +LagreSykmelding(sykmelding = eksternSykmelding.tilSykmelding())
            +SynkroniserArbeidsforhold(
                fnr = eksternSykmelding.fnr,
            )
        }
        eksternSykmelding != null && eksisterendeSykmelding != null -> {
            +LagreSykmelding(
                sykmelding =
                    eksternSykmelding
                        .tilSykmelding()
                        .copy(databaseId = eksisterendeSykmelding.databaseId),
            )
        }
        else -> {
            +SlettSykmelding(sykmeldingId = sykmeldingId)
        }
    }
}

internal fun EksternSykmelding.tilSykmelding(): Sykmelding =
    Sykmelding(
        sykmeldingId = this.sykmeldingId,
        fnr = this.fnr,
        fom = this.fom,
        tom = this.tom,
    )
