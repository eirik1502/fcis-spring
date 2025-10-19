package no.eirikhs.fktis.testoppsett

import no.eirikhs.fktis.DemoApplication
import no.eirikhs.fktis.TestContainersOppsett
import no.eirikhs.fktis.skall.kommandologg.KommandoLoggRepository
import no.eirikhs.fktis.skall.porter.ArbeidsforholdRepository
import no.eirikhs.fktis.skall.porter.SykmeldingHendelseHåndterer
import no.eirikhs.fktis.skall.porter.SykmeldingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(
    classes = [DemoApplication::class, E2eTestOppsett.TestOverrskrivConfig::class],
    properties = [
        "spring.main.allow-bean-definition-overriding=true",
    ],
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
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
