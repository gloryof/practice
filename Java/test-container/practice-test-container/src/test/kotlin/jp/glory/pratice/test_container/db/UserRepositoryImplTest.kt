package jp.glory.pratice.test_container.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.Schema
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserRepositoryImplTest {

    companion object {
        private const val dbUserName = "test-db-user"
        private const val dbPassword = "test-db-password"
        private const val dbName = "test_db_name"
        @Container
        private val mysql: MySQLContainer<*> = MySQLContainer("mysql:8.0.30")
            .withUsername(dbUserName)
            .withPassword(dbPassword)
            .withDatabaseName(dbName)
            .withClasspathResourceMapping(
                "db/init.sql",
                "/docker-entrypoint-initdb.d/init.sql",
                BindMode.READ_ONLY
            )
    }

    @BeforeAll
    fun connectDb() {
        Database.connect(
            url = mysql.jdbcUrl,
            driver = "com.mysql.cj.jdbc.Driver",
            user = dbUserName,
            password = dbPassword,
            databaseConfig = DatabaseConfig.invoke {
                defaultSchema = Schema(name = dbName)
            }
        )
    }

    @Nested
    inner class TestRegister {
        @Test
        fun whenSuccessRegistered() {
            val sut = UserRepositoryImpl()
            val expected = User(
                id = "test-register-id",
                name = "test-register-name",
                age = 30
            )

            sut.register(expected)

            val actual = sut.findById(expected.id)
            assertEquals(expected, actual)
        }
    }
}