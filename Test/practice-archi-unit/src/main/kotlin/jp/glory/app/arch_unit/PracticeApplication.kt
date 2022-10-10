package jp.glory.app.arch_unit

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jp.glory.app.arch_unit.base.adaptor.store.configDb
import jp.glory.app.arch_unit.base.ktor.configureRouting
import jp.glory.app.arch_unit.base.ktor.configureSerialization
import jp.glory.app.arch_unit.product.module.ProductModule
import jp.glory.app.arch_unit.product.adaptor.web.MemberApi
import jp.glory.app.arch_unit.product.adaptor.web.ProductApi
import jp.glory.app.arch_unit.product.adaptor.web.ServiceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin

class PracticeApplication : KoinComponent {
    fun runServer() {
        startKoin {
            modules(
                ProductModule.productModule()
            )
        }
        val productApi: ProductApi by inject()
        val memberApi: MemberApi by inject()
        val serviceApi: ServiceApi by inject()
        configDb()
        embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
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
