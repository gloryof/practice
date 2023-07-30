package jp.glory.ktor.practice.base.ktor

import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.micrometer.prometheus.PrometheusMeterRegistry
import jp.glory.ktor.practice.auth.AuthModule
import jp.glory.ktor.practice.auth.use_case.AuthenticateUseCase
import jp.glory.ktor.practice.base.ktor.auth.configureAuthorization
import jp.glory.ktor.practice.base.ktor.exception.configureException
import jp.glory.ktor.practice.base.ktor.koin.configureKoin
import jp.glory.ktor.practice.base.ktor.logging.configureLogging
import jp.glory.ktor.practice.base.ktor.monitoring.MonitoringModule
import jp.glory.ktor.practice.base.ktor.monitoring.configureMonitoring
import jp.glory.ktor.practice.base.ktor.serialization.configureSerialization
import jp.glory.ktor.practice.user.UserModule
import org.koin.ktor.ext.inject


fun Application.module() {
    configureKoin()

    val registry by inject<PrometheusMeterRegistry>()
    val authUseCase by inject<AuthenticateUseCase>()

    configureSerialization()
    configureException()
    configureLogging()
    configureMonitoring(registry)
    configureAuthorization(authUseCase)

    routing {
        route("/api") {
            UserModule.routing(this)
            AuthModule.routing(this)
        }
        route("/metrics") {
            MonitoringModule.routing(this)
        }
    }
}
