package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    
    fun createUser(user: User): User {
        return userRepository.save(user)
    }
    
    @Cacheable("users")
    @Transactional(readOnly = true)
    fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
    
    @Transactional(readOnly = true)
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
    
    @Transactional(readOnly = true)
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    
    @Transactional(readOnly = true)
    fun findAll(): List<User> {
        return userRepository.findAll().toList()
    }
    
    @Transactional(readOnly = true)
    fun findByNameContaining(name: String): List<User> {
        return userRepository.findByNameContaining(name)
    }
    
    fun updateUser(id: Long, updatedUser: User): User? {
        val existingUser = findById(id) ?: return null
        val userToUpdate = existingUser.copy(
            username = updatedUser.username,
            email = updatedUser.email,
            firstName = updatedUser.firstName,
            lastName = updatedUser.lastName
        )
        return userRepository.save(userToUpdate)
    }
    
    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
