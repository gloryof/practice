package jp.glory.ktor.practice.base.ktor.monitoring

import com.codahale.metrics.Slf4jReporter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.metrics.dropwizard.DropwizardMetrics
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.micrometer.prometheus.PrometheusMeterRegistry
import java.util.concurrent.TimeUnit

fun Application.configureMonitoring(
    appMicrometerRegistry: PrometheusMeterRegistry
) {
    install(MicrometerMetrics) {
        registry = appMicrometerRegistry
    }
    install(DropwizardMetrics) {
        Slf4jReporter.forRegistry(registry)
            .outputTo(this@configureMonitoring.log)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start(10, TimeUnit.SECONDS)
    }
}
