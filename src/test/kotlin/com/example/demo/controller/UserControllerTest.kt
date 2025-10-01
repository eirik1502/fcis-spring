package com.example.demo.controller

import com.example.demo.TestConfiguration
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@Import(TestConfiguration::class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        objectMapper = ObjectMapper()
        userRepository.deleteAll()
    }

    @Test
    fun `should get all users`() {
        // Given
        val user1 = User(username = "user1", email = "user1@example.com", firstName = "User", lastName = "One")
        val user2 = User(username = "user2", email = "user2@example.com", firstName = "User", lastName = "Two")
        userRepository.save(user1)
        userRepository.save(user2)

        // When & Then
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].username").value("user1"))
            .andExpect(jsonPath("$[1].username").value("user2"))
    }

    @Test
    fun `should get user by id`() {
        // Given
        val user = User(username = "testuser", email = "test@example.com", firstName = "Test", lastName = "User")
        val savedUser = userRepository.save(user)

        // When & Then
        mockMvc.perform(get("/api/users/${savedUser.id}"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedUser.id))
            .andExpect(jsonPath("$.username").value("testuser"))
            .andExpect(jsonPath("$.email").value("test@example.com"))
    }

    @Test
    fun `should return 404 when user not found by id`() {
        // When & Then
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should get user by username`() {
        // Given
        val user = User(username = "testuser", email = "test@example.com", firstName = "Test", lastName = "User")
        userRepository.save(user)

        // When & Then
        mockMvc.perform(get("/api/users/username/testuser"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value("testuser"))
    }

    @Test
    fun `should search users by name`() {
        // Given
        val user1 = User(username = "johnsmith", email = "john@example.com", firstName = "John", lastName = "Smith")
        val user2 = User(username = "janesmith", email = "jane@example.com", firstName = "Jane", lastName = "Smith")
        userRepository.save(user1)
        userRepository.save(user2)

        // When & Then
        mockMvc.perform(get("/api/users/search").param("name", "Smith"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].lastName").value("Smith"))
            .andExpect(jsonPath("$[1].lastName").value("Smith"))
    }

    @Test
    fun `should create user`() {
        // Given
        val user = User(username = "newuser", email = "new@example.com", firstName = "New", lastName = "User")

        // When & Then
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.username").value("newuser"))
    }

    @Test
    fun `should update user`() {
        // Given
        val existingUser = User(username = "olduser", email = "old@example.com", firstName = "Old", lastName = "User")
        val savedUser = userRepository.save(existingUser)
        val updatedUser = User(username = "updateduser", email = "updated@example.com", firstName = "Updated", lastName = "User")

        // When & Then
        mockMvc.perform(
            put("/api/users/${savedUser.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedUser.id))
            .andExpect(jsonPath("$.username").value("updateduser"))
    }

    @Test
    fun `should return 404 when updating non-existent user`() {
        // Given
        val user = User(username = "updateduser", email = "updated@example.com", firstName = "Updated", lastName = "User")

        // When & Then
        mockMvc.perform(
            put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete user`() {
        // Given
        val user = User(username = "testuser", email = "test@example.com", firstName = "Test", lastName = "User")
        val savedUser = userRepository.save(user)

        // When & Then
        mockMvc.perform(delete("/api/users/${savedUser.id}"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should return 404 when deleting non-existent user`() {
        // When & Then
        mockMvc.perform(delete("/api/users/999"))
            .andExpect(status().isNotFound)
    }
}
