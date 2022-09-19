package jp.glory.app.open_telemetry.practice.base.opentelemetry

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.SpanProcessor
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes


fun createOpenTelemetry(): OpenTelemetry {
    val resource = createResource()

    return OpenTelemetrySdk.builder()
        .setTracerProvider(createTracerProvider(resource))
        .setPropagators(
            ContextPropagators.create(W3CBaggagePropagator.getInstance())
        )
        .buildAndRegisterGlobal()
}

private fun createResource(): Resource =
    Resource.getDefault()
        .merge(
            Resource.create(
                Attributes.of(ResourceAttributes.SERVICE_NAME, "open-telemetry-practice-app")
            )
        )

private fun createTracerProvider(
    resource: Resource
): SdkTracerProvider =
    SdkTracerProvider.builder()
        .addSpanProcessor(createLogSpanProcessor())
        .addSpanProcessor(createZipkinSpanProcessor())
        .setResource(resource)
        .build()

private fun createLogSpanProcessor(): SpanProcessor =
    BatchSpanProcessor
        .builder(LoggingSpanExporter.create())
        .build()
private fun createZipkinSpanProcessor(): SpanProcessor =
    BatchSpanProcessor
        .builder(
            ZipkinSpanExporter.builder()
                .setEndpoint("http://localhost:30411/api/v2/spans")
                .build()
        )
        .build()