package jp.glory.practice.boot.app.user

import jp.glory.practice.boot.app.auth.query.web.AuthenticateFilter
import jp.glory.practice.boot.app.user.UserBeanRegister.Command.confitureCommand
import jp.glory.practice.boot.app.user.UserBeanRegister.Query.confitureQuery
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.command.domain.service.UserService
import jp.glory.practice.boot.app.user.command.infra.event.UserEventHandlerImpl
import jp.glory.practice.boot.app.user.command.infra.reposiotry.UserRepositoryImpl
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import jp.glory.practice.boot.app.user.command.web.UserCreateRouter
import jp.glory.practice.boot.app.user.data.UserDao
import jp.glory.practice.boot.app.user.query.usecase.GetUser
import jp.glory.practice.boot.app.user.query.web.GetOwnRouter
import org.springframework.beans.factory.BeanRegistrarDsl
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

/**
 * Required
 * - AuthBeanRegister
 */
object UserBeanRegister {
    fun BeanRegistrarDsl.configureUser() {
        dao()
        confitureCommand()
        confitureQuery()

        registerBean {
            webRouting(
                userCreateRouter = bean(),
                getOwnRouter = bean(),
                authenticateFilter = bean()
            )
        }
    }

    fun webRouting(
        userCreateRouter: UserCreateRouter,
        getOwnRouter: GetOwnRouter,
        authenticateFilter: AuthenticateFilter
    ) = router {

        path("/api/v1/register")
            .nest {
                contentType(MediaType.APPLICATION_JSON)
                POST(userCreateRouter::create)
            }

        path("/api/v1/user")
            .nest {
                contentType(MediaType.APPLICATION_JSON)
                filter { req, next ->
                    authenticateFilter.filter(req, next)
                }
                GET(getOwnRouter::getOwn)
            }
    }

    private fun BeanRegistrarDsl.dao() {
        registerBean<UserDao>()
    }

    private object Command {
        fun BeanRegistrarDsl.confitureCommand() {
            eventHandlers()
            repository()
            domainService()
            usecase()
            webFunction()
        }

        private fun BeanRegistrarDsl.eventHandlers() {
            registerBean<UserEventHandlerImpl>()
        }

        private fun BeanRegistrarDsl.repository() {
            registerBean<UserRepositoryImpl>()
        }

        private fun BeanRegistrarDsl.domainService() {
            registerBean<UserIdGenerator>()
            registerBean<UserService>()
        }

        private fun BeanRegistrarDsl.usecase() {
            registerBean<CreateUser>()
        }

        private fun BeanRegistrarDsl.webFunction() {
            registerBean<UserCreateRouter>()
        }
    }

    private object Query {
        fun BeanRegistrarDsl.confitureQuery() {
            usecase()
            webFunction()
        }

        private fun BeanRegistrarDsl.usecase() {
            registerBean<GetUser>()
        }

        private fun BeanRegistrarDsl.webFunction() {
            registerBean<GetOwnRouter>()
        }
    }
}
