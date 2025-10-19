package com.example.demo.skall.config

import com.example.demo.kjerne.Effekt
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.KommandoType
import com.example.demo.skall.utils.addPolymorphicDeserializer
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.addMixIn
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {
    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.modulesToInstall(KOMMANDO_DESERIALIZER_MODULE)
            builder.mixIn(Effekt::class.java, EffektMixin::class.java)
        }
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
abstract class EffektMixin

private val KOMMANDO_DESERIALIZER_MODULE =
    SimpleModule().addPolymorphicDeserializer(Kommando::type) {
        when (it) {
            KommandoType.HÅNDTER_SYKMELDING_HENDELSE -> Kommando.HåndterSykmeldingHendelse::class
            KommandoType.SYNKRONISER_ARBEIDSFORHOLD -> Kommando.SynkroniserArbeidsforhold::class
        }
    }
