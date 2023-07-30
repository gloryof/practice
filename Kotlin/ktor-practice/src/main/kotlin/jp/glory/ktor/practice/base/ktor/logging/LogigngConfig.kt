package jp.glory.ktor.practice.base.ktor.logging

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging

fun Application.configureLogging() {
    install(CallLogging)
}
