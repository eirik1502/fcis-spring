package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.LagreArbeidsforhold
import no.eirikhs.fktis.kjerne.LagreSykmelding
import no.eirikhs.fktis.kjerne.SlettSykmelding
import no.eirikhs.fktis.skall.hjelpere.lagEffektBehandler
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EffektBehandlerConfig {
    @Bean
    fun slettSykmeldingEffektBehandler(sykmeldingRepository: SykmeldingRepository) =
        lagEffektBehandler<SlettSykmelding> { effekt ->
            sykmeldingRepository.findBySykmeldingId(effekt.sykmeldingId)?.let {
                sykmeldingRepository.delete(it)
            }
        }

    @Bean
    fun lagreSykmeldingEffektBehandler(sykmeldingRepository: SykmeldingRepository) =
        lagEffektBehandler<LagreSykmelding> { effekt ->
            sykmeldingRepository.save(effekt.sykmelding)
        }

    @Bean
    fun lagreArbeidsforholdEffektBehandler(arbeidsforholdRepository: ArbeidsforholdRepository) =
        lagEffektBehandler<LagreArbeidsforhold> { effekt ->
            arbeidsforholdRepository.save(effekt.arbeidsforhold)
        }
}
