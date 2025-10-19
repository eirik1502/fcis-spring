package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.behandleSykmeldingHendelse
import no.eirikhs.fktis.skall.porter.AaregKlient
import no.eirikhs.fktis.skall.porter.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.porter.SykmeldingRepository
import no.eirikhs.fktis.skall.rammeverk.lagPlanlegger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlanleggerConfig {
    @Bean
    fun behandleSykmeldingHendelsePlanlegger(sykmeldingRepository: SykmeldingRepository) =
        lagPlanlegger(Kommando.HåndterSykmeldingHendelse::class) { kommando ->
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
    ) = lagPlanlegger(Kommando.SynkroniserArbeidsforhold::class) { kommando ->
        synkroniserArbeidsforhold(
            fnr = kommando.fnr,
            aaregArbeidsforhold = aaregKlient.hentArbeidsforhold(fnr = kommando.fnr),
            eksisterendeArbeidsforhold = arbeidsforholdRepository.findAllByFnr(kommando.fnr),
        )
    }
}
