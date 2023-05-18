package com.example.exposed

import com.example.exposed.Products.name
import com.example.exposed.Products.price
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test

class Product(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Product>(Products)
    var name by Products.name
    var price by Products.price
}
class DaoTest {
    @Test
    fun `exposed DAO`() {
        Database.connect(url = "jdbc:h2:tcp://localhost/~/test-exposed", driver = "org.h2.Driver", user = "sa")

        transaction {
            // show sql log
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Products)

            // INSERT INTO PRODUCT ("NAME", PRICE) VALUES ('test_name', 100000)
            // ...
            (1..5).map {
                Product.new {
                    name = "test_name"
                    price = (it * 100000).toBigDecimal()
                }
            }

            Products

            // SELECT PRODUCT.ID, PRODUCT."NAME", PRODUCT.PRICE FROM PRODUCT WHERE PRODUCT.PRICE >= 300000
            // UPDATE PRODUCT SET "NAME"='product' WHERE ID = 3
            // ...
            Product.find { price greaterEq 300000 }
                .forEach { it.name = "product" }

            // SELECT PRODUCT.ID, PRODUCT."NAME", PRODUCT.PRICE FROM PRODUCT WHERE PRODUCT."NAME" = 'product'
            Product.find { name eq "product" }
                .forEach { println(it) }

            // SELECT PRODUCT.ID, PRODUCT."NAME", PRODUCT.PRICE FROM PRODUCT WHERE PRODUCT.ID >= 3
            // DELETE FROM PRODUCT WHERE PRODUCT.ID = 3
            // ...
            Product.find { Products.id greaterEq 3 }
                .forEach { it.delete() }

            // DROP TABLE IF EXISTS PRODUCT
            SchemaUtils.drop(Products)
        }
    }
}