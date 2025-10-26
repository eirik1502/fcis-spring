package no.eirikhs.fktis.skall

import no.eirikhs.fktis.fktis.skall.KommandoService
import no.eirikhs.fktis.fktis.skall.hjelpere.lagEffektUtfører
import no.eirikhs.fktis.kjerne.LagreArbeidsforhold
import no.eirikhs.fktis.kjerne.LagreSykmelding
import no.eirikhs.fktis.kjerne.SlettSykmelding
import no.eirikhs.fktis.kjerne.UtførKommando
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EffektBehandlerConfig {
    @Bean
    fun utførKommandoEffektUtfører(kommandoService: KommandoService) =
        lagEffektUtfører<UtførKommando> { effekt ->
            kommandoService.utførKommando(effekt.kommando)
        }

    @Bean
    fun slettSykmeldingEffektUtfører(sykmeldingRepository: SykmeldingRepository) =
        lagEffektUtfører<SlettSykmelding> { effekt ->
            sykmeldingRepository.findBySykmeldingId(effekt.sykmeldingId)?.let {
                sykmeldingRepository.delete(it)
            }
        }

    @Bean
    fun lagreSykmeldingEffektUtfører(sykmeldingRepository: SykmeldingRepository) =
        lagEffektUtfører<LagreSykmelding> { effekt ->
            sykmeldingRepository.save(effekt.sykmelding)
        }

    @Bean
    fun lagreArbeidsforholdEffektUtfører(arbeidsforholdRepository: ArbeidsforholdRepository) =
        lagEffektUtfører<LagreArbeidsforhold> { effekt ->
            arbeidsforholdRepository.save(effekt.arbeidsforhold)
        }
}
