package jp.glory.oauth.practice.authorization.api.token

import jp.glory.oauth.practice.authorization.api.token.response.ErrorResponse
import jp.glory.oauth.practice.authorization.api.token.response.TokenResponse
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.base.mapBoth
import jp.glory.oauth.practice.authorization.spec.Scope
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import jp.glory.oauth.practice.authorization.spec.refres_token.RefreshToken
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import jp.glory.oauth.practice.authorization.store.RefreshTokenStore
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

open class RegisterTokenBase(
    private val accessTokenStore: AccessTokenStore,
    private val refreshTokenStore: RefreshTokenStore
) {
    protected fun registerToken(
        scopes: List<Scope>
    ): Either<Errors, AccessToken> {
        val token = AccessToken.generate(scopes)

        return accessTokenStore.register(token)
            .mapBoth(
                right = { Right(token) },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Register access token is failed."
                        )
                    )
                }
            )
    }

    protected fun registerRefreshToken(
        accessToken: AccessToken
    ): Either<Errors, TokenRegisterResult> {
        val refreshToken = RefreshToken.generate(accessToken.value)

        return refreshTokenStore.save(refreshToken)
            .mapBoth(
                right = {
                    Right(
                        TokenRegisterResult(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    )
                },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Register refresh token is failed."
                        )
                    )
                }
            )

    }

    protected fun createSuccessResponse(
        result: TokenRegisterResult,
        userId: String
    ): ResponseEntity<TokenResponse> =
        TokenResponse(
            accessToken = result.accessToken.value,
            tokenType = "Bearer",
            expiresIn = result.accessToken.expiresIn,
            refreshToken = result.refreshToken.value,
            scope = result.accessToken.scopes.map { it.name },
            userId = userId
        )
            .let { ResponseEntity.ok(it) }

    protected fun createErrorResponse(
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

    class TokenRegisterResult(
        val accessToken: AccessToken,
        val refreshToken: RefreshToken
    )
}