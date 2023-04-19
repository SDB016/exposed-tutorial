package com.example.exposed.service

import com.example.exposed.entity.PatchUserDTO
import com.example.exposed.entity.User
import com.example.exposed.entity.UserDTO
import com.example.exposed.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<UserDTO> = userRepository.getAllUsers().map { it.toDto() }

    fun getUser(id: Long): UserDTO = userRepository.findById(id).toDto()

    fun createUser(user: UserDTO): UserDTO {
        requireNotNull(user.name) {"Name is required"}
        requireNotNull(user.age) {"Age is required"}
        return userRepository.createUser(user.name, user.age).toDto()
    }

    fun updateUser(id: Long, user: UserDTO): UserDTO = userRepository.updateUser(id, user.name, user.age).toDto()

    fun patchUser(id: Long, user: PatchUserDTO): UserDTO = userRepository.patchUser(id, user.name, user.age).toDto()

    fun deleteUser(id: Long) = userRepository.deleteUser(id)

}

fun User.toDto() = UserDTO(id.value, name, age)