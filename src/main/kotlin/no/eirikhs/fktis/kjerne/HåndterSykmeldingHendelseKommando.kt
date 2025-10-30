package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

data class HåndterSykmeldingHendelseKommando(
    val sykmeldingId: String,
    val sykmelding: EksternSykmelding? = null,
) : Kommando

data class HåndterSykmeldingHendelseDeps(
    val eksisterendeSykmelding: Sykmelding? = null,
)

fun håndterSykmeldingHendelseAvhengigheter(kommando: HåndterSykmeldingHendelseKommando) =
    byggAvhengigheter<HåndterSykmeldingHendelseDeps> {
        bind(
            HåndterSykmeldingHendelseDeps::eksisterendeSykmelding,
            FinnSykmelding(sykmeldingId = kommando.sykmeldingId),
        )
    }

fun håndterSykmeldingHendelsePlanlegger(
    kommando: HåndterSykmeldingHendelseKommando,
    deps: HåndterSykmeldingHendelseDeps,
) = byggPlan {
    when {
        kommando.sykmelding != null && deps.eksisterendeSykmelding == null -> {
            +LagreSykmelding(sykmelding = kommando.sykmelding.tilSykmelding())
            +SynkroniserArbeidsforhold(
                fnr = kommando.sykmelding.fnr,
            )
        }
        kommando.sykmelding != null && deps.eksisterendeSykmelding != null -> {
            +LagreSykmelding(
                sykmelding =
                    kommando.sykmelding
                        .tilSykmelding()
                        .copy(databaseId = deps.eksisterendeSykmelding.databaseId),
            )
        }
        else -> {
            +SlettSykmelding(sykmeldingId = kommando.sykmeldingId)
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
