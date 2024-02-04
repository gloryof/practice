package jp.glory.practice.fullstack.server.user.adaptor.web

import jp.glory.practice.fullstack.server.base.adaptor.web.WebResponse
import jp.glory.practice.fullstack.server.user.usecase.RegisterUser
import java.time.LocalDate

class RegisterUserApi(
    private val usecase: RegisterUser
) {
    fun register(): String = "test"

    fun register(
        request: Request
    ): WebResponse<ResponseBody> =
        usecase
            .register(request.toEvent())
            .let { WebResponse.ok(ResponseBody(it.registeredId)) }

    data class Request(
        val name: String,
        val birthday: LocalDate,
        val password: String
    ) {
        fun toEvent(): RegisterUser.Input =
            RegisterUser.Input(
                name = name,
                birthday = birthday,
                password = password
            )
    }

    class ResponseBody(
        val id: String
    )
}