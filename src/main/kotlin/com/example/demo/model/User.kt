package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    
    @Column("username")
    val username: String,
    
    @Column("email")
    val email: String,
    
    @Column("first_name")
    val firstName: String,
    
    @Column("last_name")
    val lastName: String,
    
    @Column("created_at")
    val createdAt: LocalDateTime? = null,
    
    @Column("updated_at")
    val updatedAt: LocalDateTime? = null
) {
    val fullName: String
        get() = "$firstName $lastName"
}
