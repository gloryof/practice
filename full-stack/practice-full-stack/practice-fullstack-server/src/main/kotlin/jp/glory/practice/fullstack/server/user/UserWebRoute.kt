package jp.glory.practice.fullstack.server.user

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import jp.glory.practice.fullstack.server.user.adaptor.web.RegisterUserApi
import org.koin.ktor.ext.inject


fun Route.userRoute() {
    val endpoint by inject<RegisterUserApi>()

    post("/user/register") {
        call.receive<RegisterUserApi.Request>()
            .let { endpoint.register(it) }
            .let {
                call.respond(
                    status = it.status,
                    message = it.body
                )
            }
    }
}