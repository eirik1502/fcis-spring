package com.example.demo.skall

import com.example.demo.KommandoTestData
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.skall.config.JsonConfig
import com.example.demo.skall.rammeverk.KommandoService
import com.example.demo.utils.objectMapper
import org.amshove.kluent.shouldHaveSingleItem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(KommandoController::class)
@Import(JsonConfig::class)
@ActiveProfiles("test")
class KommandoControllerTest {
    @TestConfiguration
    class TestConfig {
        @Bean
        fun kommandoService(): KommandoServiceFake = KommandoServiceFake()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var kommandoService: KommandoServiceFake

    @AfterEach
    fun afterEach() {
        kommandoService.reset()
    }

    @TestFactory
    fun `burde utføre kommando`() =
        KommandoTestData
            .kallAlle()
            .map { kommando ->
                DynamicTest.dynamicTest(kommando.type.name) {
                    val request =
                        KommandoRequest(kommando = kommando)
                    mockMvc
                        .post("/kommando") {
                            contentType = MediaType.APPLICATION_JSON
                            content = objectMapper.writeValueAsString(request)
                        }.andExpect {
                            status { isOk() }
                        }

                    kommandoService.utførteKommandoer().shouldHaveSingleItem()
                    afterEach()
                }
            }
}

class KommandoServiceFake : KommandoService {
    private val utførteKommandoer = mutableListOf<Kommando>()
    private val planlagteKommandoer = mutableListOf<Kommando>()

    fun utførteKommandoer(): List<Kommando> = utførteKommandoer.toList()

    fun reset() {
        utførteKommandoer.clear()
        planlagteKommandoer.clear()
    }

    override fun utførKommando(
        kommando: Kommando,
        kildeSystem: String?,
        aktørtype: String?,
        aktørIdent: String?,
    ) {
        utførteKommandoer.add(kommando)
    }

    override fun planleggKommando(kommando: Kommando): Plan {
        planlagteKommandoer.add(kommando)
        return Plan(emptyList())
    }
}
