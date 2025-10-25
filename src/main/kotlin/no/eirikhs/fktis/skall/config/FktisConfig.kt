package no.eirikhs.fktis.skall.config

import no.eirikhs.fktis.fktis.skall.EffektUtførerRegister
import no.eirikhs.fktis.fktis.skall.KommandoService
import no.eirikhs.fktis.fktis.skall.KommandoServiceImpl
import no.eirikhs.fktis.fktis.skall.PlanleggerRegister
import no.eirikhs.fktis.fktis.skall.spring.SpringContextEffektUtførerRegister
import no.eirikhs.fktis.fktis.skall.spring.SpringContextPlanleggerRegister
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepositoryLogger
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FktisConfig {
    @Bean
    fun planleggerRegister(applicationContext: ApplicationContext): PlanleggerRegister =
        SpringContextPlanleggerRegister(appContext = applicationContext)

    @Bean
    fun effektUtførerRegister(applicationContext: ApplicationContext): EffektUtførerRegister =
        SpringContextEffektUtførerRegister(appContext = applicationContext)

    @Bean
    fun kommandoService(
        planleggerRegister: PlanleggerRegister,
        effektUtførerRegister: EffektUtførerRegister,
        kommandoLoggRepository: KommandoLoggRepository,
    ): KommandoService =
        KommandoServiceImpl(
            planleggerRegister = planleggerRegister,
            effektUtførerRegister = effektUtførerRegister,
            kommandoLogger = KommandoLoggRepositoryLogger(kommandoLoggRepository),
        )
}
