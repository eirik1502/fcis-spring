package no.eirikhs.fktis.skall

import com.fasterxml.jackson.databind.ObjectMapper
import no.eirikhs.fktis.KommandoTestData
import no.eirikhs.fktis.fktis.skall.KommandoService
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class KommandoControllerTest : E2eTestOppsett() {
    @MockitoBean
    private lateinit var kommandoService: KommandoService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @AfterEach
    fun afterEach() {
        reset(kommandoService)
    }

    @TestFactory
    fun `burde utføre kommando`() =
        KommandoTestData
            .alleKommandoer()
            .map { kommando ->
                DynamicTest.dynamicTest(kommando::class.simpleName) {
                    mockMvc
                        .post("/kommando") {
                            contentType = MediaType.APPLICATION_JSON
                            content =
                                objectMapper.writeValueAsString(kommando).also {
                                    println("Kommando: $kommando")
                                    println("Kommando JSON: $it")
                                }
                        }.andExpect {
                            status { isOk() }
                        }
                    verify(kommandoService).utførKommando(kommando)
                    reset(kommandoService)
                }
            }
}
