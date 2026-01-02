package jp.glory.practice.boot.app.auth

import jp.glory.practice.boot.app.auth.AuthBeanRegister.Command.confitureCommand
import jp.glory.practice.boot.app.auth.AuthBeanRegister.Query.confitureQuery
import jp.glory.practice.boot.app.auth.command.infra.event.AuthEventHandlerImpl
import jp.glory.practice.boot.app.auth.command.infra.repository.UserCredentialRepositoryImpl
import jp.glory.practice.boot.app.auth.command.usecase.IssueToken
import jp.glory.practice.boot.app.auth.command.web.LoginRouter
import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.auth.data.TokenDao
import jp.glory.practice.boot.app.auth.query.usecase.Authenticate
import jp.glory.practice.boot.app.auth.query.web.AuthenticateFilter
import org.springframework.beans.factory.BeanRegistrarDsl
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

object AuthBeanRegister {
    fun BeanRegistrarDsl.configureAuth() {
        dao()
        confitureCommand()
        confitureQuery()
        webFilter()

        registerBean {
            webRouting(
                loginRouter = bean()
            )
        }
    }

    fun webRouting(
        loginRouter: LoginRouter
    ) = router {
        path("/api/v1/auth").nest {
            contentType(MediaType.APPLICATION_JSON)
            POST("/login", loginRouter::login)
        }
    }

    private fun BeanRegistrarDsl.dao() {
        registerBean<AuthDao>()
        registerBean<TokenDao>()
    }


    private fun BeanRegistrarDsl.webFilter() {
        registerBean<AuthenticateFilter>()
    }

    private object Command {
        fun BeanRegistrarDsl.confitureCommand() {
            eventHandlers()
            repository()
            usecase()
            webFunction()
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

    private object Query {

        fun BeanRegistrarDsl.confitureQuery() {
            usecase()
        }

        private fun BeanRegistrarDsl.usecase() {
            registerBean<Authenticate>()
        }
    }
}
