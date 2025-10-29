package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.FinnSykmelding
import no.eirikhs.fktis.kjerne.Spørring
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Spørringer {
    @Bean
    fun finnSykmelding(sykmeldingRepository: SykmeldingRepository) =
        SpørringBehandler<FinnSykmelding, Sykmelding> { spørring ->
            sykmeldingRepository.findBySykmeldingId(spørring.sykmeldingId)!!
        }
}

class SpørringBehandler<S : Spørring<R>, R>(
    private val behandlerFunksjon: (S) -> R,
)
