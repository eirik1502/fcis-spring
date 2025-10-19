package com.example.demo.skall.kommandologg

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.skall.config.EFFEKT_DESERIALIZER_MODULE
import com.example.demo.skall.config.KOMMANDO_DESERIALIZER_MODULE
import com.example.demo.skall.utils.JsonbReadingConverter
import com.example.demo.skall.utils.JsonbWritingConverter
import com.example.demo.utils.objectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
