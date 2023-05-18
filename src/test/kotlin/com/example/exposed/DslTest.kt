package com.example.exposed


import com.example.exposed.Products.name
import com.example.exposed.Products.price
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import java.math.BigDecimal

object Products : LongIdTable(name = "product") {
    val name = varchar("name", 50)
    val price = decimal("price", 10, 2)
}

class DslTest {
    @Test
    fun `exposed DSL`() {
        Database.connect(url = "jdbc:h2:tcp://localhost/~/test-exposed", driver = "org.h2.Driver", user = "sa")

        transaction {
            // Show SQL log
            addLogger(StdOutSqlLogger)

            // CREATE TABLE IF NOT EXISTS PRODUCT (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "NAME" VARCHAR(50) NOT NULL, PRICE DECIMAL(10, 2) NOT NULL)
            SchemaUtils.create(Products)

            // INSERT INTO PRODUCT ("NAME", PRICE) VALUES ('test_name', 100000)
            // ...
            (1..5).map {
                Products.insert { products ->
                    products[price] = (it * 100000).toBigDecimal()
                    products[name] = "test_name"
                }
            }

            // UPDATE PRODUCT SET "NAME"='product' WHERE PRODUCT.PRICE >= 300000
            // ...
            Products.update({ price greaterEq 300000 }) {
                it[name] = "product"
            }

            // SELECT PRODUCT.ID, PRODUCT."NAME", PRODUCT.PRICE FROM PRODUCT WHERE PRODUCT."NAME" = 'product'
            Products.select { name eq "product" }
                .forEach{ println(it) }

            // DELETE FROM PRODUCT WHERE PRODUCT.ID >= 4
            Products.deleteWhere { id greaterEq 4 }

            // DROP TABLE IF EXISTS PRODUCT
            SchemaUtils.drop(Products)
        }
    }
}