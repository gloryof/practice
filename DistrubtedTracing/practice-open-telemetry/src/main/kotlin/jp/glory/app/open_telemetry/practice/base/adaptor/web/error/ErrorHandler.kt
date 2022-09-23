package jp.glory.app.open_telemetry.practice.base.adaptor.web.error

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.handleError(
    error: WebError
) {
    val status: HttpStatusCode = when(error) {
        is InternalServerError -> HttpStatusCode.InternalServerError
        is WebNotFound -> HttpStatusCode.NotFound
        is WebValidationError -> HttpStatusCode.BadRequest
    }
    respond(status, error.toErrorResponse())
}