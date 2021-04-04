package jp.glory.oauth.practice.authorization.api.token

import jp.glory.oauth.practice.authorization.api.token.request.ClientRequest
import jp.glory.oauth.practice.authorization.api.token.request.CodeRequest
import jp.glory.oauth.practice.authorization.api.token.request.OwnerRequest
import jp.glory.oauth.practice.authorization.api.token.request.RefreshRequest
import jp.glory.oauth.practice.authorization.api.token.response.ErrorResponse
import jp.glory.oauth.practice.authorization.api.token.response.RefreshedTokenResponse
import jp.glory.oauth.practice.authorization.api.token.response.TokenResponse
import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.spec.Scope
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import jp.glory.oauth.practice.authorization.store.AuthCodeStore
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@RestController
@RequestMapping("/api/token")
class TokenApi(
    private val codeStore: AuthCodeStore,
    private val tokenStore: AccessTokenStore
) {
    @PostMapping("/code")
    fun generateByCode(
        @RequestBody request: CodeRequest,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<out Any> =
        request
            .validate()
            .flatMap { findAuthCode(authorization.split("Bearer ")[1]) }
            .flatMap { revokeAuthCode(it) }
            .flatMap { registerToken(it.code.scopes) }
            .mapBoth(
                right = {
                    createSuccessResponse(
                        token = it,
                        userId = "test-user-id" // Temporally implement
                    )
                },
                left = { createErrorResponse(it) }
            )

    @PostMapping("/owner")
    fun generateByResourceOwnerCredential(
        @RequestBody request: OwnerRequest,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<TokenResponse> =
        TokenResponse(
            accessToken = UUID.randomUUID().toString(),
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = UUID.randomUUID().toString(),
            scope = listOf("read", "write"),
            userId = "test-user-id"
        )
            .let { ResponseEntity.ok(it) }

    @PostMapping("/client")
    fun generateByClientCredential(
        @RequestBody request: ClientRequest
    ): ResponseEntity<TokenResponse> =
        TokenResponse(
            accessToken = UUID.randomUUID().toString(),
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = UUID.randomUUID().toString(),
            scope = listOf("read", "write"),
            userId = "test-user-id"
        )
            .let { ResponseEntity.ok(it) }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody request: RefreshRequest,
    ): ResponseEntity<RefreshedTokenResponse> =
        RefreshedTokenResponse(
            accessToken = UUID.randomUUID().toString(),
            scope = listOf("read", "write")
        )
            .let { ResponseEntity.ok(it) }

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

    private fun registerToken(
        scopes: List<Scope>
    ): Either<Errors, AccessToken> {
        val token = AccessToken.generate(scopes)

        return tokenStore.register(token)
            .mapBoth(
                right = { Right(token) },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Register token is failed."
                        )
                    )
                }
            )
    }

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

    private fun createSuccessResponse(
        token: AccessToken,
        userId: String
    ): ResponseEntity<TokenResponse> =
        TokenResponse(
            accessToken = token.value,
            tokenType = "Bearer",
            expiresIn = LocalDateTime.now().until(token.expiresAt, ChronoUnit.SECONDS),
            refreshToken = UUID.randomUUID().toString(), // Temporally implement
            scope = token.scopes.map { it.name },
            userId = userId
        )
            .let { ResponseEntity.ok(it) }

    private fun createErrorResponse(
        error: Errors
    ): ResponseEntity<ErrorResponse> {
        val status = when (error.type) {
            ErrorType.InvalidRequest -> HttpStatus.BAD_REQUEST
            ErrorType.UnauthorizedClient -> HttpStatus.UNAUTHORIZED
            ErrorType.ServerError -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity
            .status(status)
            .body(
                ErrorResponse(
                    error = error.type.value,
                    errorDescription = error.message
                )
            )

    }

    data class Errors(
        val type: ErrorType,
        val message: String
    )

    enum class ErrorType(val value: String) {
        InvalidRequest("invalid_request"),
        UnauthorizedClient("unauthorized_client"),
        ServerError("server_error")
    }
}