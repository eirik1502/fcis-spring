package no.eirikhs.fktis.testoppsett

import no.eirikhs.fktis.DemoApplication
import no.eirikhs.fktis.TestContainersOppsett
import no.eirikhs.fktis.skall.repositories.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
    classes = [DemoApplication::class, E2eTestOppsett.TestOverrskrivConfig::class],
    properties = [
        "spring.main.allow-bean-definition-overriding=true",
    ],
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = true)
abstract class E2eTestOppsett {
    @Autowired
    private lateinit var aaregKlient: AaregKlientFake

    @Autowired
    private lateinit var sykmeldingRepository: SykmeldingRepository

    @Autowired
    private lateinit var arbeidsforholdRepository: ArbeidsforholdRepository

    @Autowired
    private lateinit var kommandoLoggRepository: KommandoLoggRepository

    companion object {
        init {
            TestContainersOppsett.settOpp()
        }
    }

    @TestConfiguration
    class TestOverrskrivConfig {
        @Bean
        fun aaregKlient() = AaregKlientFake()
    }

    protected fun resettTilstand() {
        aaregKlient.reset()
        sykmeldingRepository.deleteAll()
        arbeidsforholdRepository.deleteAll()
        kommandoLoggRepository.deleteAll()
    }
}
