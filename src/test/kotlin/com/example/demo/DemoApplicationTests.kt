package com.example.demo

import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfiguration::class)
class DemoApplicationTests {

    @Test
    fun contextLoads() {
        // This test verifies that the Spring context loads successfully
        // If this test passes, it means all beans are properly configured
        true.shouldBeTrue()
    }
}
