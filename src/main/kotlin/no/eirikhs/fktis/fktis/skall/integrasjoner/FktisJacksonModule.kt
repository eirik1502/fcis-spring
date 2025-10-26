package no.eirikhs.fktis.fktis.skall.integrasjoner

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.addSerializer
import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

fun lagKommandoJacksonModule(
    kommandoVedNavn: Map<String, KClass<out Kommando>>,
    feltNavn: String = "type",
): SimpleModule {
    val navnVedKommando: Map<KClass<out Kommando>, String> = kommandoVedNavn.entries.associate { (k, v) -> v to k }

    val kommandoSerializer =
        CustomValueSerializer<Kommando>(
            fieldName = feltNavn,
        ) { kommando ->
            navnVedKommando[kommando::class]
                ?: throw IllegalArgumentException("Ingen navn funnet for kommando: ${kommando::class.simpleName}")
        }

    val kommandoDeserializer =
        ClassSwitchDeserializer(typeField = "type") { kommandoNavn ->
            kommandoVedNavn[kommandoNavn]
                ?: throw IllegalArgumentException("Ingen kommando funnet for navn: $kommandoNavn")
        }

    return SimpleModule().apply {
        addSerializer(Kommando::class, kommandoSerializer)
        addDeserializer(Kommando::class, kommandoDeserializer)
    }
}

fun lagEffektJacksonModule(
    effektVedNavn: Map<String, KClass<out Effekt>>,
    feltNavn: String = "type",
): SimpleModule {
    val navnVedEffekt: Map<KClass<out Effekt>, String> = effektVedNavn.entries.associate { (k, v) -> v to k }

    val effektSerializer =
        CustomValueSerializer<Effekt>(
            fieldName = feltNavn,
        ) { effekt ->
            navnVedEffekt[effekt::class]
                ?: throw IllegalArgumentException("Ingen navn funnet for effekt: ${effekt::class.simpleName}")
        }

    val effektDeserializer =
        ClassSwitchDeserializer(typeField = "type") { effektNavn ->
            effektVedNavn[effektNavn]
                ?: throw IllegalArgumentException("Ingen effekt funnet for navn: $effektNavn")
        }

    return SimpleModule().apply {
        addSerializer(Effekt::class, effektSerializer)
        addDeserializer(Effekt::class, effektDeserializer)
    }
}

class CustomValueSerializer<T : Any>(
    private val fieldName: String,
    private val getFieldValue: (T) -> String,
) : JsonSerializer<T>() {
    override fun serialize(
        value: T,
        gen: JsonGenerator,
        serializers: SerializerProvider,
    ) {
        val mapper = gen.codec as ObjectMapper
        val objectNode = mapper.createObjectNode()

        objectNode.put(fieldName, getFieldValue(value))
        @Suppress("UNCHECKED_CAST")
        (value::class as KClass<T>).memberProperties.forEach { prop ->
            val propValue = prop.get(value)
            objectNode.set<JsonNode>(prop.name, mapper.valueToTree(propValue))
        }
        gen.writeTree(objectNode)
    }
}

class ClassSwitchDeserializer<T : Any>(
    private val typeField: String = "type",
    private val getClass: (type: String) -> KClass<out T>,
) : JsonDeserializer<T>() {
    override fun deserialize(
        p: JsonParser,
        ctxt: DeserializationContext,
    ): T {
        val node: ObjectNode = p.codec.readTree(p)
        val typeNode: JsonNode? = node.get(typeField)

        val type: String =
            if (typeNode != null && !typeNode.isNull) {
                typeNode.asText()
            } else {
                throw IllegalArgumentException("JSON is missing the required '$typeField' field or its value is null.")
            }

        if (type.isEmpty()) {
            throw IllegalArgumentException("The '$typeField' field in JSON is empty.")
        }

        val clazz = getClass(type)
        return p.codec.treeToValue(node, clazz.java)
    }
}
