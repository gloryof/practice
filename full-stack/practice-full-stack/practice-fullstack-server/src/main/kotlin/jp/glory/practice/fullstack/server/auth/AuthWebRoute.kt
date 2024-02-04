package jp.glory.practice.fullstack.server.auth

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import jp.glory.practice.fullstack.server.auth.adaptor.web.LoginApi
import org.koin.ktor.ext.inject

fun Route.authRoute() {
    val endpoint by inject<LoginApi>()

    post("/auth/login") {
        call.receive<LoginApi.Request>()
            .let { endpoint.login(it) }
            .let {
                call.respond(
                    status = it.status,
                    message = it.body
                )
            }
    }
}