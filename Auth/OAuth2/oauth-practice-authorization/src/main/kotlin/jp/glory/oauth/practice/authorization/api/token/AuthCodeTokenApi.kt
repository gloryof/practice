package jp.glory.oauth.practice.authorization.api.token

import jp.glory.oauth.practice.authorization.api.token.request.CodeRequest
import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import jp.glory.oauth.practice.authorization.store.AuthCodeStore
import jp.glory.oauth.practice.authorization.store.RefreshTokenStore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/token/code")
class AuthCodeTokenApi(
    private val codeStore: AuthCodeStore,
    accessTokenStore: AccessTokenStore,
    refreshTokenStore: RefreshTokenStore
) : RegisterTokenBase(accessTokenStore, refreshTokenStore) {

    @PostMapping
    fun generate(
        @RequestBody request: CodeRequest,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<out Any> =
        request
            .validate()
            .flatMap { findAuthCode(authorization.split("Bearer ")[1]) }
            .flatMap { revokeAuthCode(it) }
            .flatMap { registerToken(it.code.scopes) }
            .flatMap { registerRefreshToken(it) }
            .mapBoth(
                right = {
                    createSuccessResponse(
                        result = it,
                        userId = "test-user-id" // Tentative value
                    )
                },
                left = { createErrorResponse(it) }
            )


    private fun findAuthCode(
        authCodeValue: String
    ): Either<Errors, AuthCodeStore.RegisteredAuthCode> =
        codeStore
            .findAuthCode(authCodeValue)
            .mapBoth(
                right = {
                    if (it != null) {
                        Right(it)
                    } else  {
                        Left(
                            Errors(
                                type = ErrorType.InvalidRequest,
                                message = "Auth code is invalid."
                            )
                        )
                    }
                },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Find auth code is failed."
                        )
                    )
                }
            )

    private fun revokeAuthCode(
        registeredCode: AuthCodeStore.RegisteredAuthCode,
    ): Either<Errors, AuthCodeStore.RegisteredAuthCode> =
        codeStore.deleteAuthCode(registeredCode.code)
            .mapBoth(
                right = { Right(registeredCode) },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Revoke auth code is failed."
                        )
                    )
                }
            )
}