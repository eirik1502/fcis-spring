package com.example.demo.skall

import com.example.demo.DemoApplication
import com.example.demo.TestContainersOppsett
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.skall.config.JsonConfig
import com.example.demo.utils.objectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(KommandoController::class)
@Import(JsonConfig::class)
@ActiveProfiles("test")
class KommandoControllerTest {
    @TestConfiguration
    class TestConfig {
        @Bean
        fun kommandoUtfører(): KommandoUtfører = object : KommandoUtfører {
            override fun utførKommando(kommando: Kommando): Plan {
                println("Utfører kommando: $kommando")
                return Plan(emptyList())
            }
        }
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `burde utføre dummy-kommando`() {
        val request = DummyRequest(kommando = "Test dummy kommando")
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/dummy-kommando")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)),
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `burde utføre kommando`() {
        val kommando = Kommando.HåndterSykmeldingHendelse(
            sykmeldingId = "123",
            sykmelding = null,
        )
        val request = KommandoRequest(kommando = kommando)
        mockMvc.post("/kommando") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }
}