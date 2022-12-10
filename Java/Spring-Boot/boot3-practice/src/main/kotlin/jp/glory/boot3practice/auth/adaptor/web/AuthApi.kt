package jp.glory.boot3practice.auth.adaptor.web

import jp.glory.boot3practice.auth.use_case.AuthenticateUseCase
import jp.glory.boot3practice.base.adaptor.web.WebApi
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebExceptionHelper
import jp.glory.boot3practice.base.use_case.UseCaseError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono


@WebApi
@RequestMapping(EndpointConst.Auth.authenticate)
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

    @PostMapping
    fun authenticate(
        @RequestBody request: AuthRequest
    ): Mono<ResponseEntity<AuthResponse>> =
        Mono.just(authenticateUser(request))
            .flatMap { result ->
                result.mapBoth(
                    success = { Mono.just(it) },
                    failure = { Mono.error(WebExceptionHelper.create(it))}
                )
            }
            .map {
                ResponseEntity.ok(AuthResponse(it.tokenValue))
            }

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