package jp.glory.app.arch_unit.base.ktor

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Application.configureSerialization() {
    val dateFormat = "yyyy/MM/dd"
    install(ContentNegotiation) {
        jackson {
            registerModule(
                JavaTimeModule()
                    .apply {
                        addSerializer(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
                    }
            )
        }
    }
}