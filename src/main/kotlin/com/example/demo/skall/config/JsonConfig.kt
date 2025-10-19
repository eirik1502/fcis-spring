package com.example.demo.skall.config

import com.example.demo.kjerne.*
import com.example.demo.skall.utils.addPolymorphicDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {
    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.modulesToInstall(KOMMANDO_DESERIALIZER_MODULE)
        }
}

val KOMMANDO_DESERIALIZER_MODULE =
    SimpleModule().addPolymorphicDeserializer(Kommando::type) {
        when (it) {
            KommandoType.NOOP -> Kommando.NoOp::class
            KommandoType.HÅNDTER_SYKMELDING_HENDELSE -> Kommando.HåndterSykmeldingHendelse::class
            KommandoType.SYNKRONISER_ARBEIDSFORHOLD -> Kommando.SynkroniserArbeidsforhold::class
        }
    }

val EFFEKT_DESERIALIZER_MODULE =
    SimpleModule().addPolymorphicDeserializer(Effekt::type) {
        when (it) {
            EffektType.SLETT_SYKMELDING_REGISTRERINGER -> SlettSykmeldingRegistreringer::class
            EffektType.UTFØR_KOMMANDO -> UtførKommando::class
            EffektType.LAGRE_SYKMELDING -> LagreSykmelding::class
            EffektType.SLETT_SYKMELDING -> SlettSykmelding::class
            EffektType.LAGRE_SYKMELDING_REGISTRERING -> LagreSykmeldingRegistrering::class
            EffektType.LAGRE_ARBEIDSFORHOLD -> LagreArbeidsforhold::class
            EffektType.SLETT_ARBEIDSFORHOLD -> SlettArbeidsforhold::class
        }
    }
