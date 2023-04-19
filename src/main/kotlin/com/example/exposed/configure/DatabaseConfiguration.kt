package com.example.exposed.configure

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.Driver

@Configuration
class DatabaseConfiguration(
    @Value("\${spring.datasource.url}") private val url: String,
    @Value("\${spring.datasource.driver-class-name}") private val driver: String,
    @Value("\${spring.datasource.username}") private val user: String,
) {
    @Bean
    fun database(): Database {
        return Database.connect(url, driver = driver, user = user)
    }
}