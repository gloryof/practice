package jp.glory.practice.fullstack.server.auth.adaptor.web

import jp.glory.practice.fullstack.server.auth.usecase.Login
import jp.glory.practice.fullstack.server.base.adaptor.web.WebResponse


class LoginApi(
    private val usecase: Login
) {
    fun login(
        request: Request
    ): WebResponse<ResponseBody> =
        usecase.login(
            Login.Input(
                userId = request.userId,
                password = request.password
            )
        )
            .let { WebResponse.ok(ResponseBody(it.token)) }

    class Request(
        val userId: String,
        val password: String
    )

    class ResponseBody(
        val token: String
    )
}