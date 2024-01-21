package jp.glory.practice.fullstack.server.auth.adaptor.web

import jp.glory.practice.fullstack.server.auth.usecase.Login
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/login")
class LoginApi(
    private val usecase: Login
) {
    @PostMapping
    fun login(
        @RequestBody request: Request
    ): Response =
        usecase.login(
            Login.Input(
                userId = request.userId,
                password = request.password
            )
        )
            .let { Response(it.token) }

    class Request(
        val userId: String,
        val password: String
    )

    class Response(
        val token: String
    )
}