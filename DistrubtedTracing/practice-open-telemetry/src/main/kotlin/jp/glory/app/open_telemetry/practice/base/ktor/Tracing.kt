package jp.glory.app.open_telemetry.practice.base.ktor

import io.ktor.server.application.*
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.ktor.v2_0.KtorServerTracing


fun Application.configureTracing(openTelemetry: OpenTelemetry) {
    install(KtorServerTracing) {
        setOpenTelemetry(openTelemetry)
    }
}



