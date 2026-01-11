package jp.glory.practice.boot.app

import io.mockk.mockk
import jp.glory.practice.boot.app.auth.query.web.AuthenticateFilter
import jp.glory.practice.boot.app.user.UserBeanRegister
import jp.glory.practice.boot.app.user.command.web.UserCreateRouter
import jp.glory.practice.boot.app.user.query.web.GetOwnRouter
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse

object TestRouting {

    fun user(
        userCreateRouter: UserCreateRouter = mockk(),
        getOwnRouter: GetOwnRouter = mockk(),
        authenticateFilter: AuthenticateFilter = mockk()
    ): RouterFunction<ServerResponse> =
        UserBeanRegister.webRouting(
            userCreateRouter = userCreateRouter,
            getOwnRouter = getOwnRouter,
            authenticateFilter = authenticateFilter
        )
}
