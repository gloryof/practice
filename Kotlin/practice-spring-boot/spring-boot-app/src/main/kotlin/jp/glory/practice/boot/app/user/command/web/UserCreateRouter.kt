package jp.glory.practice.boot.app.user.command.web

import com.github.michaelbull.result.mapBoth
import jp.glory.practice.boot.app.base.spring.web.WebErrorHandler
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.time.LocalDate

class UserCreateRouter(
    private val usecase: CreateUser
) {

    fun create(request: ServerRequest): ServerResponse =
        request.body(Request::class.java)
            .toInput()
            .let { usecase.createUser(it) }
            .mapBoth(
                success = { createSuccessResponse(it) },
                failure = { WebErrorHandler.createErrorResponse(it) }
            )

    private fun createSuccessResponse(id: String): ServerResponse =
        ServerResponse.ok()
            .body(Response(id))

    data class Request(
        val loginId: String,
        val userName: String,
        val password: String,
        val birthday: LocalDate
    ) {
        fun toInput(): CreateUser.Input =
            CreateUser.Input(
                loginId = loginId,
                userName = userName,
                password = password,
                birthday = birthday
            )
    }

    data class Response(
        val userId: String
    )
}
