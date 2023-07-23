package jp.glory.ktor.practice.auth.adaptor.web

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import jp.glory.ktor.practice.auth.use_case.AuthenticateUseCase
import jp.glory.ktor.practice.base.adaptor.web.WebExceptionHelper
import jp.glory.ktor.practice.base.use_case.UseCaseError


class AuthApi(
    private val authenticateUseCase: AuthenticateUseCase
) {
    data class AuthRequest(
        val id: String? = null,
        val password: String? = null
    )
    data class AuthResponse(
        val token: String
    )

    fun authenticate(
        request: AuthRequest
    ): AuthResponse =
        authenticateUser(request)
            .mapBoth(
                success = { AuthResponse(it.tokenValue) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    private fun authenticateUser(
        request: AuthRequest
    ): Result<AuthenticateUseCase.UserAuthOutput, UseCaseError> =
        authenticateUseCase.authenticateUser(
            AuthenticateUseCase.UserAuthInput(
                id = request.id ?: "",
                password = request.password ?: ""
            )
        )
}
