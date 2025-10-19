package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
import com.example.demo.skall.porter.AaregKlient
import com.example.demo.skall.porter.ArbeidsforholdRepository
import com.example.demo.skall.porter.SykmeldingRepository
import com.example.demo.skall.rammeverk.lagPlanlegger
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
