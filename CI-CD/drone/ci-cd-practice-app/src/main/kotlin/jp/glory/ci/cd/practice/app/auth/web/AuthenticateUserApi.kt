package jp.glory.ci.cd.practice.app.auth.web

import com.github.michaelbull.result.mapBoth
import jp.glory.ci.cd.practice.app.auth.usecase.AuthenticateUser
import jp.glory.ci.cd.practice.app.base.web.WebExceptionHelper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authenticate")
class AuthenticateUserApi(
    private val authenticateUser: AuthenticateUser
) {
    data class Request(
        val userId: String,
        val password: String
    )

    data class Response(
        val tokenValue: String
    )

    @PostMapping
    fun authenticate(
        @RequestBody request: Request
    ): ResponseEntity<Response> =
        authenticateUser.authenticate(
            AuthenticateUser.Input(
                userId = request.userId,
                password = request.password
            )
        )
            .mapBoth(
                success = { createSuccessResponse(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    private fun createSuccessResponse(
        output: AuthenticateUser.Output
    ): ResponseEntity<Response> =
        ResponseEntity.ok(
            Response(output.tokenValue)
        )
}