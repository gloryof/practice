package jp.glory.practice.fullstack.server.base.adaptor.web

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class WebResponse<T> private constructor(
    val status: HttpStatusCode,
    val body: T
) {
    companion object {
        fun <T> ok(body: T) =
            WebResponse(
                status = HttpStatusCode.OK,
                body = body
            )
    }
}
