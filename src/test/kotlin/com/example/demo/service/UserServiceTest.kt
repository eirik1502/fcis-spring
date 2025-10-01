package com.example.demo.service

import com.example.demo.TestConfiguration
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@Import(TestConfiguration::class)
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `should create user successfully`() {
        // Given
        val user = User(
            username = "testuser",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )

        // When
        val result = userService.createUser(user)

        // Then
        result.shouldNotBeNull()
        result.id.shouldNotBeNull()
        result.username.shouldBeEqualTo("testuser")
        result.email.shouldBeEqualTo("test@example.com")
        result.firstName.shouldBeEqualTo("Test")
        result.lastName.shouldBeEqualTo("User")
    }

    @Test
    fun `should find user by id`() {
        // Given
        val user = User(
            username = "testuser",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )
        val savedUser = userRepository.save(user)

        // When
        val result = userService.findById(savedUser.id!!)

        // Then
        result.shouldNotBeNull()
        result.id.shouldBeEqualTo(savedUser.id)
        result.username.shouldBeEqualTo("testuser")
    }

    @Test
    fun `should return null when user not found by id`() {
        // When
        val result = userService.findById(999L)

        // Then
        result.shouldBeNull()
    }

    @Test
    fun `should find user by username`() {
        // Given
        val user = User(
            username = "testuser",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )
        userRepository.save(user)

        // When
        val result = userService.findByUsername("testuser")

        // Then
        result.shouldNotBeNull()
        result.username.shouldBeEqualTo("testuser")
    }

    @Test
    fun `should update user successfully`() {
        // Given
        val existingUser = User(
            username = "olduser",
            email = "old@example.com",
            firstName = "Old",
            lastName = "User"
        )
        val savedUser = userRepository.save(existingUser)
        val updatedUser = User(
            username = "newuser",
            email = "new@example.com",
            firstName = "New",
            lastName = "User"
        )

        // When
        val result = userService.updateUser(savedUser.id!!, updatedUser)

        // Then
        result.shouldNotBeNull()
        result.id.shouldBeEqualTo(savedUser.id)
        result.username.shouldBeEqualTo("newuser")
        result.email.shouldBeEqualTo("new@example.com")
        result.firstName.shouldBeEqualTo("New")
        result.lastName.shouldBeEqualTo("User")
    }

    @Test
    fun `should return null when updating non-existent user`() {
        // Given
        val updatedUser = User(
            username = "newuser",
            email = "new@example.com",
            firstName = "New",
            lastName = "User"
        )

        // When
        val result = userService.updateUser(999L, updatedUser)

        // Then
        result.shouldBeNull()
    }

    @Test
    fun `should delete user successfully`() {
        // Given
        val user = User(
            username = "testuser",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )
        val savedUser = userRepository.save(user)

        // When
        val result = userService.deleteUser(savedUser.id!!)

        // Then
        result.shouldBeEqualTo(true)
    }

    @Test
    fun `should return false when deleting non-existent user`() {
        // When
        val result = userService.deleteUser(999L)

        // Then
        result.shouldBeEqualTo(false)
    }
}
