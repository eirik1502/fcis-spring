package com.example.demo.skall

import com.example.demo.TestConfiguration
import com.example.demo.Testdata
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.kjerne.sykmelding.SykmeldingDTO
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
import com.example.demo.sykmelding
import com.example.demo.sykmeldingDTO
import io.mockk.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfiguration::class)
class KommandoUtførerTest {
    @Autowired
    lateinit var kommandoUtfører: KommandoUtfører

    @Autowired
    lateinit var sykmeldingRepository: SykmeldingRepository

    @BeforeAll
    fun setopp() {
        mockkStatic(::behandleSykmeldingHendelse)
        every { behandleSykmeldingHendelse(any()) } returns Plan()
    }

    @AfterEach
    fun reset() {
        sykmeldingRepository.deleteAll()
    }

    @AfterAll
    fun rivNed() {
        unmockkAll()
    }

    @Nested
    inner class TestHåndterSykmeldingHendelse {

        @Test
        fun `burde behandle uten eksisterende`() {
            kommandoUtfører.utfør(Kommando.HåndterSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = Testdata.sykmeldingDTO()
            ))

            verify { behandleSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = matchNotNull(),
                eksisterendeSykmelding = null
            ) }
        }

        @Test
        fun `burde behandle med eksisterende`() {
            sykmeldingRepository.save(
                Testdata.sykmelding(sykmeldingId = "1")
            )

            kommandoUtfører.utfør(Kommando.HåndterSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = Testdata.sykmeldingDTO()
            ))

            verify { behandleSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = matchNotNull(),
                eksisterendeSykmelding = matchNotNull()
            ) }
        }

        @Test
        fun `burde håndtere tombstone`() {
            kommandoUtfører.utfør(Kommando.HåndterSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = null
            ))

            verify { behandleSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = null,
                eksisterendeSykmelding = any()
            ) }
        }
    }
}

inline fun <reified T : Any> MockKMatcherScope.matchNotNull() = this.matchNullable<T> { it != null }
