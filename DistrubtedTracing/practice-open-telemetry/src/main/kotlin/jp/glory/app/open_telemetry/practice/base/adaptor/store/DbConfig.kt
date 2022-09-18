package jp.glory.app.open_telemetry.practice.base.adaptor.store

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.Schema

fun configDb() {
    Database.connect(
        url = "jdbc:mysql://localhost:30306/test_db_name",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "root-password",
        databaseConfig = DatabaseConfig.invoke {
            defaultSchema = Schema(name = "test_db_name")
        }
    )
}