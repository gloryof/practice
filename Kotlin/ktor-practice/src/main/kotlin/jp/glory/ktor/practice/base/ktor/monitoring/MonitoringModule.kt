package jp.glory.ktor.practice.base.ktor.monitoring

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.koin.dsl.module
import org.koin.ktor.ext.inject

object MonitoringModule {
    fun createModule() = module {
        single { PrometheusMeterRegistry(PrometheusConfig.DEFAULT) }
    }


    fun routing(route: Route) {
        route.apply {
            val appMicrometerRegistry by inject<PrometheusMeterRegistry>()
            route {
                get("/metrics-micrometer") {
                    call.respond(appMicrometerRegistry.scrape())
                }
            }
        }
    }
}
