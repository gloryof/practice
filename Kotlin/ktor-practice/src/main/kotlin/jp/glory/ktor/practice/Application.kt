package jp.glory.ktor.practice

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jp.glory.ktor.practice.base.ktor.module

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
