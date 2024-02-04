package jp.glory.practice.fullstack.server.base.ktor.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import jp.glory.practice.fullstack.server.auth.AuthModule
import jp.glory.practice.fullstack.server.base.adaptor.store.DaoModule
import jp.glory.practice.fullstack.server.review.ReviewModule
import jp.glory.practice.fullstack.server.user.UserModule
import org.koin.ktor.plugin.Koin

fun Application.configureInjection() {
    install(Koin) {
        modules(
            DaoModule.createModule(),
            UserModule.createModule(),
            ReviewModule.createModule(),
            AuthModule.createModule()
        )
    }
}