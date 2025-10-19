package com.example.demo.skall

import com.example.demo.kjerne.LagreArbeidsforhold
import com.example.demo.kjerne.LagreSykmelding
import com.example.demo.kjerne.SlettSykmelding
import com.example.demo.skall.porter.ArbeidsforholdRepository
import com.example.demo.skall.porter.SykmeldingRepository
import com.example.demo.skall.rammeverk.lagEffektUtfører
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EffektUtførerConfig {
    @Bean
    fun slettSykmeldingEffektUtfører(sykmeldingRepository: SykmeldingRepository) =
        lagEffektUtfører(SlettSykmelding::class) { effekt ->
            sykmeldingRepository.findBySykmeldingId(effekt.sykmeldingId)?.let {
                sykmeldingRepository.delete(it)
            }
        }

    @Bean
    fun lagreSykmeldingEffektUtfører(sykmeldingRepository: SykmeldingRepository) =
        lagEffektUtfører(LagreSykmelding::class) { effekt ->
            sykmeldingRepository.save(effekt.sykmelding)
        }

    @Bean
    fun lagreArbeidsforholdEffektUtfører(arbeidsforholdRepository: ArbeidsforholdRepository) =
        lagEffektUtfører<LagreArbeidsforhold> { effekt ->
            arbeidsforholdRepository.save(effekt.arbeidsforhold)
        }
}
