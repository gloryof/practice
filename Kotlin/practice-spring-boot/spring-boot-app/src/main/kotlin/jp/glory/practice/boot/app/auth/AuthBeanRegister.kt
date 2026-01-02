package jp.glory.practice.boot.app.auth

import jp.glory.practice.boot.app.auth.command.infra.event.AuthEventHandlerImpl
import jp.glory.practice.boot.app.auth.command.infra.repository.UserCredentialRepositoryImpl
import jp.glory.practice.boot.app.auth.command.usecase.IssueToken
import jp.glory.practice.boot.app.auth.command.web.LoginRouter
import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.auth.data.TokenDao
import org.springframework.beans.factory.BeanRegistrarDsl
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.router

object AuthBeanRegister {
    fun BeanRegistrarDsl.configureAuth() {
        dao()
        eventHandlers()
        repository()
        usecase()
        webFunction()

        registerBean {
            webRouting(
                loginRouter = bean()
            )
        }
    }

    fun webRouting(
        loginRouter: LoginRouter
    ) = router {
        val pathBase = "/api/v1/auth"
        accept(APPLICATION_JSON).nest {
            POST("$pathBase/login", loginRouter::login)
        }
    }

    private fun BeanRegistrarDsl.dao() {
        registerBean<AuthDao>()
        registerBean<TokenDao>()
    }

    private fun BeanRegistrarDsl.eventHandlers() {
        registerBean<AuthEventHandlerImpl>()
    }

    private fun BeanRegistrarDsl.repository() {
        registerBean<UserCredentialRepositoryImpl>()
    }

    private fun BeanRegistrarDsl.usecase() {
        registerBean<IssueToken>()
    }

    private fun BeanRegistrarDsl.webFunction() {
        registerBean<LoginRouter>()
    }
}
