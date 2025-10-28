package no.eirikhs.fktis.kjerne

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.junit.jupiter.api.Test

val objectMapper =
    jacksonMapperBuilder()
        .addModules(JavaTimeModule())
        .build()
        .writerWithDefaultPrettyPrinter()

class SpørringTest {
    @Test
    fun `example test`() {
        val spørring =
            synkroniserArbeidsforholdSpørring(
                kommando = SynkroniserArbeidsforhold(fnr = "fnr-1"),
            )

        println(objectMapper.writeValueAsString(spørring))

        populerSpørringBeskrivelse(spørring)

        println(objectMapper.writeValueAsString(spørring))

        val plan = spørring.bind.invoke()
        println(plan)
    }
}
