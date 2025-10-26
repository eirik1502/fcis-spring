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
    fun planBehandler(
        effektDistributør: EffektDistributør,
        kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
    ): PlanBehandler =
        PlanBehandler(
            effektDistributør = effektDistributør,
            kommandoPlanleggerDistributør = kommandoPlanleggerDistributør,
        )

    @Bean
    fun kommandoLogger(kommandoLoggRepository: KommandoLoggRepository): KommandoLogger =
        KommandoLoggRepositoryLogger(kommandoLoggRepository)

    @Bean
    fun kommandoService(
        kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
        planBehandler: PlanBehandler,
        kommandoLogger: KommandoLogger,
    ): KommandoService =
        KommandoServiceImpl(
            kommandoPlanleggerDistributør = kommandoPlanleggerDistributør,
            planBehandler = planBehandler,
            kommandoLogger = kommandoLogger,
        )
}
