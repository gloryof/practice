package jp.glory.app.open_telemetry.practice.base.usecase

import io.opentelemetry.api.trace.Span

class UseCaseTelemetry {
    fun registerUseCaseAttribute(
        useCaseName: String,
        methodName: String,
    ) {
        Span.current().setAttribute("app.use_case.name", useCaseName)
        Span.current().setAttribute("app.use_case.method", methodName)
    }
}