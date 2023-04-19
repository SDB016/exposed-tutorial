package com.example.exposed.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object LongIdTable {
    object Users : LongIdTable() {
        val name = varchar("name", 50)
        val age = integer("age")
    }
}