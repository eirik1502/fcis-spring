package no.eirikhs.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.hjelpere.lagKommandoBehandler
import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelse
import no.eirikhs.fktis.kjerne.NoOpKommando
import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.behandleSykmeldingHendelse
import no.eirikhs.fktis.skall.porter.AaregKlient
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KommandoBehandlerConfig {
    @Bean
    fun noOpPlanlegger() =
        lagKommandoBehandler<NoOpKommando> {
            Plan.TOM
        }

    @Bean
    fun behandleSykmeldingHendelsePlanlegger(sykmeldingRepository: SykmeldingRepository) =
        lagKommandoBehandler<HåndterSykmeldingHendelse> { kommando ->
            behandleSykmeldingHendelse(
                sykmeldingId = kommando.sykmeldingId,
                eksternSykmelding = kommando.sykmelding,
                eksisterendeSykmelding = sykmeldingRepository.findBySykmeldingId(kommando.sykmeldingId),
            )
        }

    @Bean
    fun synkroniserArbeidsforholdPlanlegger(
        aaregKlient: AaregKlient,
        arbeidsforholdRepository: ArbeidsforholdRepository,
    ) = lagKommandoBehandler<SynkroniserArbeidsforhold> { kommando ->
        synkroniserArbeidsforhold(
            fnr = kommando.fnr,
            aaregArbeidsforhold = aaregKlient.hentArbeidsforhold(fnr = kommando.fnr),
            eksisterendeArbeidsforhold = arbeidsforholdRepository.findAllByFnr(kommando.fnr),
        )
    }
}
