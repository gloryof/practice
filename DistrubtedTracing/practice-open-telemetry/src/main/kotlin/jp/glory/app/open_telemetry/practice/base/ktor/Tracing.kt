package jp.glory.app.open_telemetry.practice.base.ktor

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.common.AttributesBuilder
import io.opentelemetry.context.Context
import io.opentelemetry.instrumentation.api.instrumenter.AttributesExtractor
import io.opentelemetry.instrumentation.ktor.v2_0.KtorServerTracing


fun Application.configureTracing(openTelemetry: OpenTelemetry) {
    install(KtorServerTracing) {
        setOpenTelemetry(openTelemetry)
        addAttributeExtractor(PracticeAppAttributesExtractor())
        setCapturedRequestHeaders(listOf("Content-Type", "Accept"))
        setCapturedResponseHeaders(listOf("Content-Type"))
    }
}

class PracticeAppAttributesExtractor : AttributesExtractor<ApplicationRequest, ApplicationResponse> {
    override fun onStart(
        attributes: AttributesBuilder,
        parentContext: Context,
        request: ApplicationRequest
    ) {

    }

    override fun onEnd(
        attributes: AttributesBuilder,
        context: Context,
        request: ApplicationRequest,
        response: ApplicationResponse?,
        error: Throwable?
    ) {
        request.call.parameters
            .forEach { key, values ->
                values.forEachIndexed { index,value ->
                    attributes.put("app.http.param.$key[$index]", value)
                }
            }
    }

}