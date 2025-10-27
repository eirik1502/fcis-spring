package no.eirikhs.fktis.skall.config

import no.eirikhs.fktis.skall.*
import no.eirikhs.fktis.skall.integrasjoner.SpringTransaksjonBehandler
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepositoryLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FktisConfig {
    @Bean
    fun kommandoDistributør(kommandoBehandlere: List<no.eirikhs.fktis.skall.KommandoPlanlegger<*>>): KommandoPlanleggerDistributør =
        KommandoPlanleggerDistributør(behandlere = kommandoBehandlere)

    @Bean
    fun effektDistributør(effektBehandlere: List<EffektBehandler<*>>): EffektDistributør = EffektDistributør(behandlere = effektBehandlere)

    @Bean
    fun planBehandler(
        effektDistributør: EffektDistributør,
        kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
        transaksjonBehandler: TransaksjonBehandler,
        kommandoLogger: no.eirikhs.fktis.skall.KommandoLogger,
    ): PlanBehandler =
        PlanBehandler(
            effektDistributør = effektDistributør,
            kommandoPlanleggerDistributør = kommandoPlanleggerDistributør,
            transaksjonBehandler = transaksjonBehandler,
            kommandoLogger = kommandoLogger,
        )

    @Bean
    fun kommandoLogger(kommandoLoggRepository: KommandoLoggRepository): no.eirikhs.fktis.skall.KommandoLogger =
        KommandoLoggRepositoryLogger(kommandoLoggRepository)

    @Bean
    fun transaksjonBehandler(platformTransactionManager: PlatformTransactionManager): TransaksjonBehandler =
        SpringTransaksjonBehandler(txManager = platformTransactionManager)

    @Bean
    fun kommandoService(
        kommandoPlanleggerDistributør: KommandoPlanleggerDistributør,
        planBehandler: PlanBehandler,
    ): no.eirikhs.fktis.skall.KommandoService =
        no.eirikhs.fktis.skall.KommandoServiceImpl(
            kommandoPlanleggerDistributør = kommandoPlanleggerDistributør,
            planBehandler = planBehandler,
        )
}
