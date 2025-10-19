package com.example.demo.testoppsett

import com.example.demo.DemoApplication
import com.example.demo.TestContainersOppsett
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    classes = [DemoApplication::class, E2eTestOppsett.TestOverrskrivConfig::class],
    properties = [
        "spring.main.allow-bean-definition-overriding=true",
    ],
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
abstract class E2eTestOppsett {
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
}
