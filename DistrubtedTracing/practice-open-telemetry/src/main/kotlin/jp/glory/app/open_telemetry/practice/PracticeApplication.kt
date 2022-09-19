package jp.glory.app.open_telemetry.practice

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.opentelemetry.api.OpenTelemetry
import jp.glory.app.open_telemetry.practice.base.adaptor.store.configDb
import jp.glory.app.open_telemetry.practice.base.ktor.configureRouting
import jp.glory.app.open_telemetry.practice.base.ktor.configureSerialization
import jp.glory.app.open_telemetry.practice.base.ktor.configureTracing
import jp.glory.app.open_telemetry.practice.base.opentelemetry.createOpenTelemetry
import jp.glory.app.open_telemetry.practice.product.ProductModule
import jp.glory.app.open_telemetry.practice.product.adaptor.web.MemberApi
import jp.glory.app.open_telemetry.practice.product.adaptor.web.ProductApi
import jp.glory.app.open_telemetry.practice.product.adaptor.web.ServiceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin

class PracticeApplication : KoinComponent {
    fun runServer() {
        startKoin {
            modules(ProductModule.productModule())
        }
        val productApi: ProductApi by inject()
        val memberApi: MemberApi by inject()
        val serviceApi: ServiceApi by inject()
        val openTelemetry: OpenTelemetry = createOpenTelemetry()
        configDb()
        embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
            configureTracing(openTelemetry)
            configureSerialization()
            configureRouting(
                productApi = productApi,
                memberApi = memberApi,
                serviceApi = serviceApi
            )

        }.start(wait = true)
    }
}

fun main() {
    PracticeApplication().runServer()
}
