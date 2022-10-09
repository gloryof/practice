package jp.glory.app.coverage.arch_unit.base.adaptor.store

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.Schema


fun configDb() {
    val config = HikariConfig()
    config.jdbcUrl = "jdbc:mysql://localhost:30306/test_db_name"
    config.driverClassName = "com.mysql.cj.jdbc.Driver"
    config.username = "root"
    config.password = "root-password"

    Database.connect(
        datasource = HikariDataSource(config),
        databaseConfig = DatabaseConfig.invoke {
            defaultSchema = Schema(name = "test_db_name")
        }
    )
}