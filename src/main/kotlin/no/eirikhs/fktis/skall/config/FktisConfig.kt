package no.eirikhs.fktis.skall.config

import no.eirikhs.fktis.fktis.skall.*
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepositoryLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FktisConfig {
    @Bean
    fun kommandoDistributør(kommandoBehandlere: List<KommandoPlanlegger<*>>): KommandoPlanleggerDistributør =
        KommandoPlanleggerDistributør(behandlere = kommandoBehandlere)

    @Bean
    fun effektDistributør(effektBehandlere: List<EffektBehandler<*>>): EffektDistributør = EffektDistributør(behandlere = effektBehandlere)

    @Bean
    fun kommandoService(
        kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
        effektDistributør: EffektDistributør,
        kommandoLoggRepository: KommandoLoggRepository,
    ): KommandoService =
        KommandoServiceImpl(
            kommandoPlanleggerDistributør = kommandoPlanleggerDistributør,
            effektDistributør = effektDistributør,
            kommandoLogger = KommandoLoggRepositoryLogger(kommandoLoggRepository),
        )
}
