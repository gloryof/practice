package jp.glory.practice.fullstack.server.base.ktor.plugin

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import jp.glory.practice.fullstack.server.base.adaptor.web.ErrorResponse
import java.lang.IllegalStateException
import kotlin.IllegalArgumentException

fun Application.configureWeb() {
    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val (response, status) = toErrorResponse(cause)

            call.respond(
                status = status,
                message = response
            )
        }
    }
}

private fun toErrorResponse(
    throwable: Throwable
): Pair<ErrorResponse, HttpStatusCode> =
    when (throwable) {
        is IllegalArgumentException ->
            Pair(
                ErrorResponse.createValidationError(),
                HttpStatusCode.BadRequest
            )
        is IllegalStateException ->
            Pair(
                ErrorResponse.createValidationError(),
                HttpStatusCode.BadRequest
            )
        else ->
            Pair(
                ErrorResponse.createInternalError(),
                HttpStatusCode.InternalServerError
            )
    }