package no.eirikhs.fktis.skall.config

import no.eirikhs.fktis.fktis.skall.*
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepositoryLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FktisConfig {
    @Bean
    fun kommandoDistributør(kommandoBehandlere: List<KommandoBehandler<*>>): KommandoDistributør =
        KommandoDistributør(behandlere = kommandoBehandlere)

    @Bean
    fun effektDistributør(effektBehandlere: List<EffektBehandler<*>>): EffektDistributør = EffektDistributør(behandlere = effektBehandlere)

    @Bean
    fun kommandoService(
        kommandoDistributør: KommandoDistributør,
        effektDistributør: EffektDistributør,
        kommandoLoggRepository: KommandoLoggRepository,
    ): KommandoService =
        KommandoServiceImpl(
            kommandoDistributør = kommandoDistributør,
            effektDistributør = effektDistributør,
            kommandoLogger = KommandoLoggRepositoryLogger(kommandoLoggRepository),
        )
}
