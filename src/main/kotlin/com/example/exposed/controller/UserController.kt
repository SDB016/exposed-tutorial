package com.example.exposed.controller

import com.example.exposed.entity.PatchUserDTO
import com.example.exposed.entity.UserDTO
import com.example.exposed.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> = userService.getAllUsers().toOkResponse()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> = userService.getUser(id).toOkResponse()


    @PostMapping
    fun createUser(@RequestBody userDTO:UserDTO):ResponseEntity<UserDTO> = userService.createUser(userDTO).toOkResponse()

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> = userService.updateUser(id, userDTO).toOkResponse()

    @PatchMapping("/{id}")
    fun patchUser(@PathVariable id: Long, @RequestBody userDTO: PatchUserDTO): ResponseEntity<UserDTO> = userService.patchUser(id, userDTO).toOkResponse()

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.ok().build()
    }

    fun <T> T.toOkResponse(): ResponseEntity<T> = ResponseEntity.ok(this)
}