package jp.glory.ktor.practice.base.ktor.serialization

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE

            val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            registerModules(
                JavaTimeModule()
                    .addSerializer(LocalDateSerializer(dateFormatter))
                    .addDeserializer(LocalDate::class.java, LocalDateDeserializer(dateFormatter))
            )
        }
    }
}
