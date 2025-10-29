package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

data class HåndterSykmeldingHendelseKommando(
    val sykmeldingId: String,
    val sykmelding: EksternSykmelding? = null,
) : Kommando

fun håndterSykmeldingHendelseSpørring(kommando: HåndterSykmeldingHendelseKommando) =
    byggSpørring {
        val sykmelding by +FinnSykmelding(sykmeldingId = kommando.sykmeldingId)
        bind {
            håndterSykmeldingHendelse(
                sykmeldingId = kommando.sykmeldingId,
                eksternSykmelding = kommando.sykmelding,
                eksisterendeSykmelding = sykmelding,
            )
        }
    }

fun håndterSykmeldingHendelse(
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
