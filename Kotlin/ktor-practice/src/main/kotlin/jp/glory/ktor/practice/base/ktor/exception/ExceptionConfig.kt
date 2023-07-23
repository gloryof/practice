package jp.glory.ktor.practice.base.ktor.exception

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import jp.glory.ktor.practice.base.adaptor.web.WebAuthenticateFailedException
import jp.glory.ktor.practice.base.adaptor.web.WebErrorDetail
import jp.glory.ktor.practice.base.adaptor.web.WebGenericErrorDetail
import jp.glory.ktor.practice.base.adaptor.web.WebNotAuthorizedException
import jp.glory.ktor.practice.base.adaptor.web.WebNotFoundException
import jp.glory.ktor.practice.base.adaptor.web.WebValidationException

fun Application.configureException() {
    install(StatusPages) {
        exception<RuntimeException> { call, _ ->
            WebGenericErrorDetail.createGenericErrorDetail(
                httpStatusCode = HttpStatusCode.InternalServerError,
            )
                .let { call.respondError(it) }
        }

        exception<WebAuthenticateFailedException> { call, cause ->
            call.respondError(cause.errorDetail)
        }

        exception<WebNotFoundException> { call, cause ->
            call.respondError(cause.errorDetail)
        }


        exception<WebNotAuthorizedException> { call, cause ->
            call.respondError(cause.errorDetail)
        }

        exception<WebValidationException> { call, _ ->
            WebGenericErrorDetail.createGenericErrorDetail(
                httpStatusCode = HttpStatusCode.BadRequest
            )
                .let { call.respondError(it) }
        }
    }
}

private suspend fun ApplicationCall.respondError(errorDetail: WebErrorDetail) =
    this.respond(
        status = errorDetail.getHttpStatus(),
        message = errorDetail
    )
