package no.eirikhs.fktis.skall.repositories

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.readValue
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.KommandoLogger
import no.eirikhs.fktis.fktis.skall.KommandoMetadata
import no.eirikhs.fktis.skall.config.EFFEKT_JACKON_MODULE
import no.eirikhs.fktis.skall.config.KOMMANDO_JACKSON_MODULE
import no.eirikhs.fktis.skall.utils.JsonbReadingConverter
import no.eirikhs.fktis.skall.utils.JsonbWritingConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.Instant

@Table("kommando_logg")
data class KommandoLogg(
    @Id
    val kommandoLoggId: String? = null,
    val opprettet: Instant = Instant.now(),
    val traceId: String? = null,
    val kommando: Kommando,
    val plan: Plan,
    val suksess: Boolean = true,
    val feilmelding: String? = null,
    val kildesystem: String? = null,
    val aktortype: String? = null,
    val aktorident: String? = null,
)

class KommandoLoggRepositoryLogger(
    private val repository: KommandoLoggRepository,
) : KommandoLogger {
    override fun loggKommandoUtførelse(
        kommando: Kommando,
        plan: Plan,
        suksess: Boolean,
        feil: Throwable?,
        metadata: KommandoMetadata?,
    ) {
        repository.save(
            KommandoLogg(
                traceId = null,
                kommando = kommando,
                plan = plan,
                suksess = suksess,
                feilmelding = feil?.stackTraceToString(),
                kildesystem = metadata?.kildesystem,
                aktorident = metadata?.aktørident,
                aktortype = metadata?.aktørtype,
            ),
        )
    }
}

interface KommandoLoggRepository : CrudRepository<KommandoLogg, String>

@Configuration
class KommandoLoggConverterConfig {
    @Bean
    fun kommandoLoggConversions(): JdbcCustomConversions {
        val objectMapper =
            jacksonMapperBuilder()
                .addModule(JavaTimeModule())
                .addModule(KOMMANDO_JACKSON_MODULE)
                .addModule(EFFEKT_JACKON_MODULE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build()

        class JsonbToKommandoConverter : JsonbReadingConverter<Kommando>() {
            override fun convertValue(value: String): Kommando = objectMapper.readValue(value)
        }

        class KommandoToJsonbConverter : JsonbWritingConverter<Kommando>() {
            override fun convertValue(value: Kommando): String = objectMapper.writeValueAsString(value)
        }

        class JsonbToPlanConverter : JsonbReadingConverter<Plan>() {
            override fun convertValue(value: String): Plan = objectMapper.readValue(value)
        }

        class PlanToJsonbConverter : JsonbWritingConverter<Plan>() {
            override fun convertValue(value: Plan): String = objectMapper.writeValueAsString(value)
        }
        return JdbcCustomConversions(
            listOf(
                JsonbToKommandoConverter(),
                KommandoToJsonbConverter(),
                JsonbToPlanConverter(),
                PlanToJsonbConverter(),
            ),
        )
    }
}
