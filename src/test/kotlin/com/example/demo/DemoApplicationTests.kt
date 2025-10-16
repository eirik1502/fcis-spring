package com.example.demo

import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [DemoApplication::class])
@ActiveProfiles("test")
class DemoApplicationTests {
    companion object {
        init {
            TestContainersOppsett.settOpp()
        }
    }

    @Test
    fun contextLoads() {
        // This test verifies that the Spring context loads successfully
        // If this test passes, it means all beans are properly configured
        true.shouldBeTrue()
    }
}
