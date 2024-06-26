package jp.glory.practice.fullstack.server.base.ktor.plugin

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import jp.glory.practice.fullstack.server.base.adaptor.web.ErrorResponse
import jp.glory.practice.fullstack.server.base.exception.AuthorizationException
import jp.glory.practice.fullstack.server.base.exception.InvalidRequestException
import jp.glory.practice.fullstack.server.base.exception.NotFoundException


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

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }
}

private fun toErrorResponse(
    throwable: Throwable
): Pair<ErrorResponse, HttpStatusCode> =
    when (throwable) {
        is InvalidRequestException ->
            Pair(
                ErrorResponse.createValidationError(throwable.message),
                HttpStatusCode.BadRequest
            )

        is NotFoundException ->
            Pair(
                ErrorResponse.createValidationError(throwable.message),
                HttpStatusCode.BadRequest
            )

        is AuthorizationException ->
            Pair(
                ErrorResponse.createAuthorizationError(throwable.message),
                HttpStatusCode.BadRequest
            )

        else ->
            Pair(
                ErrorResponse.createInternalError(),
                HttpStatusCode.InternalServerError
            )
    }