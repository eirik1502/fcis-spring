package no.eirikhs.fktis.testoppsett

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.KommandoLogger
import no.eirikhs.fktis.skall.KommandoMetadata
import no.eirikhs.fktis.skall.config.EFFEKT_JACKON_MODULE
import no.eirikhs.fktis.skall.config.KOMMANDO_JACKSON_MODULE
import no.eirikhs.fktis.utils.logger
import org.slf4j.Logger

class TestKommandoLogger : KommandoLogger {
    private val log = logger()
    private val objectMapper =
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .addModule(KOMMANDO_JACKSON_MODULE)
            .addModule(EFFEKT_JACKON_MODULE)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
            .writerWithDefaultPrettyPrinter()

    override fun loggKommandoUtførelse(
        kommando: Kommando?,
        plan: Plan,
        suksess: Boolean,
        feil: Throwable?,
        metadata: KommandoMetadata?,
    ) {
        if (suksess) {
            log.info(
                objectMapper.writeValueAsString(
                    mapOf(
                        "kommando" to kommando,
                        "plan" to plan,
                        "metadata" to metadata,
                    ),
                ),
            )
        } else {
            log.info(
                objectMapper.writeValueAsString(
                    mapOf(
                        "kommando" to kommando,
                        "plan" to plan,
                        "metadata" to metadata,
                        "feil" to feil?.stackTraceToString(),
                    ),
                ),
            )
        }
    }
}
