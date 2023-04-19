package com.example.exposed.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import com.example.exposed.entity.User
import com.example.exposed.entity.UserDTO
import com.example.exposed.exception.NotFoundException

@Repository
class UserRepository(private val database: Database) {

    fun findById(id: Long): User = transaction {
        User.findById(id)?: throw NotFoundException("User not found")
    }
    fun getAllUsers(): List<User> = transaction(database) {
        User.all().toList()
    }

    fun createUser(name: String, age: Int): User = transaction {
        User.new {
            this.name = name
            this.age = age
        }
    }

    fun updateUser(id: Long, name: String, age: Int): User = transaction {
        findById(id).apply {
            this.name = name
            this.age = age
        }
    }

    fun deleteUser(id: Long): Any = transaction {
        findById(id).delete()
    }

    fun patchUser(id: Long, name: String?, age: Int?): User = transaction {
        findById(id).apply {
            this.name = name?: this.name
            this.age = age?: this.age
        }
    }

}

