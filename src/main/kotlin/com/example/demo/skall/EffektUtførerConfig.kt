package com.example.demo.skall

import com.example.demo.kjerne.Effekt
import com.example.demo.kjerne.LagreSykmelding
import com.example.demo.kjerne.SlettSykmelding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

class EffektUtfører<E : Effekt>(
    val effektType: KClass<E>,
    val utfør: (E) -> Unit,
)

@Configuration
class EffektUtførerConfig {
    @Bean
    fun slettSykmeldingEffektUtfører(sykmeldingRepository: SykmeldingRepository) =
        EffektUtfører(SlettSykmelding::class) { effekt ->
            sykmeldingRepository.findBySykmeldingId(effekt.sykmeldingId)?.let {
                sykmeldingRepository.delete(it)
            }
        }

    @Bean
    fun lagreSykmeldingEffektUtfører(sykmeldingRepository: SykmeldingRepository) =
        EffektUtfører(LagreSykmelding::class) { effekt ->
            sykmeldingRepository.save(effekt.sykmelding)
        }
}
