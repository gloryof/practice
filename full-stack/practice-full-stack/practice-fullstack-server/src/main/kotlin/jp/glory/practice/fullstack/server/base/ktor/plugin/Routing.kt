package jp.glory.practice.fullstack.server.base.ktor.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import jp.glory.practice.fullstack.server.auth.authRoute
import jp.glory.practice.fullstack.server.user.userRoute


fun Application.configureRouting() {
    routing {
        route("/api") {
            authRoute()
            userRoute()
        }
    }
}