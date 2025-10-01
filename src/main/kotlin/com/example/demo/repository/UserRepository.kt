package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    
    fun findByUsername(username: String): User?
    
    fun findByEmail(email: String): User?
    
    @Query("SELECT * FROM users WHERE first_name = :firstName")
    fun findByFirstName(@Param("firstName") firstName: String): List<User>
    
    @Query("SELECT * FROM users WHERE last_name = :lastName")
    fun findByLastName(@Param("lastName") lastName: String): List<User>
    
    @Query("SELECT * FROM users WHERE first_name ILIKE :name OR last_name ILIKE :name")
    fun findByNameContaining(@Param("name") name: String): List<User>
}
