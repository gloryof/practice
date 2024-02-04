package jp.glory.practice.fullstack.server

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureInjection
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureRouting
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureSecurity
import jp.glory.practice.fullstack.server.base.ktor.plugin.configureWeb

fun main() {
	embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
		.start(wait = true)
}

fun Application.module() {
	configureInjection()
	configureSecurity()
	configureWeb()
	configureRouting()
}
