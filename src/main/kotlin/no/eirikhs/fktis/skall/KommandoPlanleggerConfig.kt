package no.eirikhs.fktis.skall

import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.hjelpere.lagKommandoPlanlegger
import no.eirikhs.fktis.kjerne.ArbeidsforholdNotifikasjonKommando
import no.eirikhs.fktis.kjerne.HåndterSykmeldingHendelse
import no.eirikhs.fktis.kjerne.NoOpKommando
import no.eirikhs.fktis.kjerne.SynkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.arbeidsforhold.håndterArbeidsforholdNotifikasjon
import no.eirikhs.fktis.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.behandleSykmeldingHendelse
import no.eirikhs.fktis.skall.porter.AaregKlient
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KommandoPlanleggerConfig {
    @Bean
    fun noOpKommandoPlanlegger() =
        lagKommandoPlanlegger<NoOpKommando> {
            Plan.TOM
        }

    @Bean
    fun behandleSykmeldingHendelseKommandoPlanlegger(sykmeldingRepository: SykmeldingRepository) =
        lagKommandoPlanlegger<HåndterSykmeldingHendelse> { kommando ->
            behandleSykmeldingHendelse(
                sykmeldingId = kommando.sykmeldingId,
                eksternSykmelding = kommando.sykmelding,
                eksisterendeSykmelding = sykmeldingRepository.findBySykmeldingId(kommando.sykmeldingId),
            )
        }

    @Bean
    fun synkroniserArbeidsforholdKommandoPlanlegger(
        aaregKlient: AaregKlient,
        arbeidsforholdRepository: ArbeidsforholdRepository,
    ) = lagKommandoPlanlegger<SynkroniserArbeidsforhold> { kommando ->
        synkroniserArbeidsforhold(
            fnr = kommando.fnr,
            aaregArbeidsforhold = aaregKlient.hentArbeidsforhold(fnr = kommando.fnr),
            eksisterendeArbeidsforhold = arbeidsforholdRepository.findAllByFnr(kommando.fnr),
        )
    }

    @Bean
    fun arbeidsforholdNotifikasjonKommandoPlanlegger() =
        lagKommandoPlanlegger<ArbeidsforholdNotifikasjonKommando> { kommando ->
            håndterArbeidsforholdNotifikasjon(fnr = kommando.fnr)
        }
}
