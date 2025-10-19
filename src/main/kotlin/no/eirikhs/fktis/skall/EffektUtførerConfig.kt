package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.LagreArbeidsforhold
import no.eirikhs.fktis.kjerne.LagreSykmelding
import no.eirikhs.fktis.kjerne.SlettSykmelding
import no.eirikhs.fktis.skall.porter.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.porter.SykmeldingRepository
import no.eirikhs.fktis.skall.rammeverk.lagEffektUtfører
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
