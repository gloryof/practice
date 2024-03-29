package jp.glory.practice.fullstack.server

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureInjection
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureRouting
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureSecurity
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureWeb
import jp.glory.practice.fullstack.server.base.ktor.plugin.createGraphQL

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureInjection()
    val graphQL = createGraphQL()

    configureSecurity()
    configureWeb()
    configureRouting(graphQL)
}
