package com.example.demo.skall

import com.example.demo.TestConfiguration
import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.kjerne.sykmelding.SykmeldingDTO
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
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
                sykmelding = lagTestSykmeldingDTO()
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
                lagTestSykmelding(sykmeldingId = "1")
            )

            kommandoUtfører.utfør(Kommando.HåndterSykmeldingHendelse(
                sykmeldingId = "1",
                sykmelding = lagTestSykmeldingDTO()
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

fun lagTestSykmeldingDTO(
    sykmeldingId: String = "sykmeldingId",
    fnr: String = "fnr",
    fom: LocalDate = LocalDate.parse("2025-08-01"),
    tom: LocalDate = LocalDate.parse("2025-08-30"),
) = SykmeldingDTO(
    sykmeldingId = sykmeldingId,
    fnr = fnr,
    fom = fom,
    tom = tom,
)

fun lagTestSykmelding(
    sykmeldingId: String = "sykmeldingId",
    fnr: String = "fnr",
    fom: LocalDate = LocalDate.parse("2025-08-01"),
    tom: LocalDate = LocalDate.parse("2025-08-30"),
) = Sykmelding(
    sykmeldingId = sykmeldingId,
    fnr = fnr,
    fom = fom,
    tom = tom,
)

inline fun <reified T : Any> MockKMatcherScope.matchNotNull() = this.matchNullable<T> { it != null }
