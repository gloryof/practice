package jp.glory.ktor.practice.base.ktor.koin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import jp.glory.ktor.practice.auth.AuthModule
import jp.glory.ktor.practice.base.ktor.monitoring.MonitoringModule
import jp.glory.ktor.practice.user.UserModule
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(
            MonitoringModule.createModule(),
            UserModule.createModule(),
            AuthModule.createModule()
        )
    }
}
