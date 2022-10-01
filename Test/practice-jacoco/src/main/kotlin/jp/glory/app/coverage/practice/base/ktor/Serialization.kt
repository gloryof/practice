package jp.glory.app.coverage.practice.base.ktor

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.github.michaelbull.result.mapBoth
import io.ktor.serialization.jackson.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import jp.glory.app.coverage.practice.product.adaptor.web.*
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