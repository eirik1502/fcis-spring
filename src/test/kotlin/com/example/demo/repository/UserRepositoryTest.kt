package com.example.demo.repository

import com.example.demo.TestConfiguration
import com.example.demo.model.User
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfiguration::class)
@ActiveProfiles("test")
@Testcontainers
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `should save and find user by id`() {
        // Given
        val user = User(
            username = "testuser",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )

        // When
        val savedUser = userRepository.save(user)
        val foundUser = userRepository.findById(savedUser.id!!)

        // Then
        foundUser.isPresent.shouldBeTrue()
        foundUser.get().username.shouldBeEqualTo("testuser")
        foundUser.get().email.shouldBeEqualTo("test@example.com")
        foundUser.get().firstName.shouldBeEqualTo("Test")
        foundUser.get().lastName.shouldBeEqualTo("User")
    }

    @Test
    fun `should find user by username`() {
        // Given
        val user = User(
            username = "johndoe",
            email = "john@example.com",
            firstName = "John",
            lastName = "Doe"
        )
        userRepository.save(user)

        // When
        val foundUser = userRepository.findByUsername("johndoe")

        // Then
        foundUser.shouldNotBeNull()
        foundUser.username.shouldBeEqualTo("johndoe")
        foundUser.email.shouldBeEqualTo("john@example.com")
    }

    @Test
    fun `should find user by email`() {
        // Given
        val user = User(
            username = "janedoe",
            email = "jane@example.com",
            firstName = "Jane",
            lastName = "Doe"
        )
        userRepository.save(user)

        // When
        val foundUser = userRepository.findByEmail("jane@example.com")

        // Then
        foundUser.shouldNotBeNull()
        foundUser.username.shouldBeEqualTo("janedoe")
        foundUser.email.shouldBeEqualTo("jane@example.com")
    }

    @Test
    fun `should return null when user not found by username`() {
        // When
        val foundUser = userRepository.findByUsername("nonexistent")

        // Then
        foundUser.shouldBeNull()
    }

    @Test
    fun `should find users by name containing`() {
        // Given
        val user1 = User(
            username = "johnsmith",
            email = "john.smith@example.com",
            firstName = "John",
            lastName = "Smith"
        )
        val user2 = User(
            username = "janesmith",
            email = "jane.smith@example.com",
            firstName = "Jane",
            lastName = "Smith"
        )
        userRepository.save(user1)
        userRepository.save(user2)

        // When
        val foundUsers = userRepository.findByNameContaining("Smith")

        // Then
        foundUsers.size.shouldBeEqualTo(2)
        foundUsers.any { it.firstName == "John" }.shouldBeTrue()
        foundUsers.any { it.firstName == "Jane" }.shouldBeTrue()
    }
}
