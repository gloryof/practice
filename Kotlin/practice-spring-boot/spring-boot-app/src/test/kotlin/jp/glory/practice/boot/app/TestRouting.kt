package jp.glory.practice.boot.app

import io.mockk.mockk
import jp.glory.practice.boot.app.user.UserBeanRegister
import jp.glory.practice.boot.app.user.command.web.UserCreateRouter
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse

object TestRouting {

    fun user(
        userCreateRouter: UserCreateRouter = mockk()
    ): RouterFunction<ServerResponse> =
        UserBeanRegister.webRouting(
            userCreateRouter = userCreateRouter
        )
}
