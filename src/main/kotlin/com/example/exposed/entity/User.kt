package com.example.exposed.entity

import com.example.exposed.dao.LongIdTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object: LongEntityClass<User>(LongIdTable.Users)

    var name by LongIdTable.Users.name
    var age by LongIdTable.Users.age
}

data class UserDTO(val id:Long, val name: String, val age: Int)

data class PatchUserDTO(val id: Long?, val name: String?, val age: Int?)