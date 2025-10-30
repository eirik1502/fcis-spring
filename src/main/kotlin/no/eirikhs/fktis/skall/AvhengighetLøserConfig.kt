package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.FinnAlleSykmeldinger
import no.eirikhs.fktis.kjerne.FinnSykmelding
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import no.eirikhs.fktis.skall.hjelpere.lagAvhengighetLøser
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AvhengighetLøserConfig {
    @Bean
    fun finnSykmelding(sykmeldingRepository: SykmeldingRepository) =
        lagAvhengighetLøser<FinnSykmelding, Sykmelding?> { avhengighet ->
            sykmeldingRepository.findBySykmeldingId(avhengighet.sykmeldingId)
        }

    @Bean
    fun finnAlleSykmeldinger(sykmeldingRepository: SykmeldingRepository) =
        lagAvhengighetLøser<FinnAlleSykmeldinger, List<Sykmelding>> { avhengighet ->
            sykmeldingRepository.findAll().toList()
        }
}
