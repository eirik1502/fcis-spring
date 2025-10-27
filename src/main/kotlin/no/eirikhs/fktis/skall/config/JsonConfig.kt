package no.eirikhs.fktis.skall.config

import no.eirikhs.fktis.kjerne.*
import no.eirikhs.fktis.kjerne.UtførKommandoSteg
import no.eirikhs.fktis.skall.integrasjoner.lagEffektJacksonModule
import no.eirikhs.fktis.skall.integrasjoner.lagKommandoJacksonModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {
    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.modulesToInstall(KOMMANDO_JACKSON_MODULE, EFFEKT_JACKON_MODULE)
        }
}

val KOMMANDO_JACKSON_MODULE =
    lagKommandoJacksonModule(
        mapOf(
            "NOOP" to NoOpKommando::class,
            "HÅNDTER_SYKMELDING_HENDELSE" to HåndterSykmeldingHendelse::class,
            "SYNKRONISER_ARBEIDSFORHOLD" to SynkroniserArbeidsforhold::class,
            "ARBEIDSFORHOLD_NOTIFIKASJON" to ArbeidsforholdNotifikasjonKommando::class,
        ),
    )

val EFFEKT_JACKON_MODULE =
    lagEffektJacksonModule(
        mapOf(
            // "UTFØR_KOMMANDO" to UtførKommandoSteg::class,
            "LAGRE_SYKMELDING" to LagreSykmelding::class,
            "SLETT_SYKMELDING" to SlettSykmelding::class,
            "LAGRE_BEKREFTELSE" to LagreBekreftelse::class,
            "SLETT_BEKREFTELSE" to SlettBekreftelse::class,
            "LAGRE_ARBEIDSFORHOLD" to LagreArbeidsforhold::class,
            "SLETT_ARBEIDSFORHOLD" to SlettArbeidsforhold::class,
        ),
    )
