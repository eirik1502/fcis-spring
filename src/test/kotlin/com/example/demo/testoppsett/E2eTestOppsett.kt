package com.example.demo.testoppsett

import com.example.demo.DemoApplication
import com.example.demo.TestContainersOppsett
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [DemoApplication::class])
@ActiveProfiles("test")
abstract class E2eTestOppsett {
    companion object {
        init {
            TestContainersOppsett.settOpp()
        }
    }
}
