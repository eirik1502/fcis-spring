package com.example.demo.skall.config

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.KommandoType
import com.example.demo.skall.utils.addPolymorphicDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        println("Creating customizer bean")
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            println("Builder customizer")
            builder.modulesToInstall(KOMMANDO_DESERIALIZER_MODULE)
        }
    }

}

private val KOMMANDO_DESERIALIZER_MODULE = SimpleModule().addPolymorphicDeserializer(Kommando::type) {
    when (it) {
        KommandoType.HÅNDTER_SYKMELDING_HENDELSE -> Kommando.HåndterSykmeldingHendelse::class
        KommandoType.SYNKRONISER_ARBEIDSFORHOLD -> Kommando.SynkroniserArbeidsforhold::class
    }
}
