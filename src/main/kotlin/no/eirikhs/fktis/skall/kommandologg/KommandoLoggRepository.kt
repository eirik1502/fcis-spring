package no.eirikhs.fktis.skall.kommandologg

import com.fasterxml.jackson.module.kotlin.readValue
import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.config.EFFEKT_DESERIALIZER_MODULE
import no.eirikhs.fktis.skall.config.KOMMANDO_DESERIALIZER_MODULE
import no.eirikhs.fktis.skall.utils.JsonbReadingConverter
import no.eirikhs.fktis.skall.utils.JsonbWritingConverter
import no.eirikhs.fktis.utils.objectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.repository.CrudRepository

interface KommandoLoggRepository : CrudRepository<KommandoLogg, String>

@Configuration
class KommandoLoggConverterConfig {
    @Bean
    fun kommandoLoggConversions(): JdbcCustomConversions =
        JdbcCustomConversions(
            listOf(
                JsonbToKommandoConverter(),
                KommandoToJsonbConverter(),
                JsonbToPlanConverter(),
                PlanToJsonbConverter(),
            ),
        )
}

private val KOMMANDO_LOGG_OBJECT_MAPPER =
    objectMapper.copy().registerModules(
        KOMMANDO_DESERIALIZER_MODULE,
        EFFEKT_DESERIALIZER_MODULE,
    )

class JsonbToKommandoConverter : JsonbReadingConverter<Kommando>() {
    override fun convertValue(value: String): Kommando = KOMMANDO_LOGG_OBJECT_MAPPER.readValue(value)
}

class KommandoToJsonbConverter : JsonbWritingConverter<Kommando>() {
    override fun convertValue(value: Kommando): String = KOMMANDO_LOGG_OBJECT_MAPPER.writeValueAsString(value)
}

class JsonbToPlanConverter : JsonbReadingConverter<Plan>() {
    override fun convertValue(value: String): Plan = KOMMANDO_LOGG_OBJECT_MAPPER.readValue(value)
}

class PlanToJsonbConverter : JsonbWritingConverter<Plan>() {
    override fun convertValue(value: Plan): String = KOMMANDO_LOGG_OBJECT_MAPPER.writeValueAsString(value)
}
