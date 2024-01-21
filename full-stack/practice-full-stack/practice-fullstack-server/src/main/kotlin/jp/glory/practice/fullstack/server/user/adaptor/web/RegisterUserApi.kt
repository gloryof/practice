package jp.glory.practice.fullstack.server.user.adaptor.web

import jp.glory.practice.fullstack.server.user.usecase.RegisterUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/register")
class RegisterUserApi(
    private val usecase: RegisterUser
) {
    @GetMapping
    fun register(): String = "test"

    @PostMapping
    fun register(
        @RequestBody request: Request
    ): Response =
        usecase
            .register(request.toEvent())
            .let {Response(it.registeredId) }

    class Request(
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

    class Response(
        val id: String
    )
}