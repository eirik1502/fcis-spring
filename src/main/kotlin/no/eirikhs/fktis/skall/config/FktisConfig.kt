package no.eirikhs.fktis.skall.config

import no.eirikhs.fktis.fktis.skall.EffektDistributør
import no.eirikhs.fktis.fktis.skall.KommandoDistributør
import no.eirikhs.fktis.fktis.skall.KommandoService
import no.eirikhs.fktis.fktis.skall.KommandoServiceImpl
import no.eirikhs.fktis.fktis.skall.spring.SpringContextEffektDistributør
import no.eirikhs.fktis.fktis.skall.spring.SpringContextKommandoDistributør
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepositoryLogger
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FktisConfig {
    @Bean
    fun planleggerRegister(applicationContext: ApplicationContext): KommandoDistributør =
        SpringContextKommandoDistributør(appContext = applicationContext)

    @Bean
    fun effektUtførerRegister(applicationContext: ApplicationContext): EffektDistributør =
        SpringContextEffektDistributør(appContext = applicationContext)

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
