package jp.glory.oauth.practice.authorization.api.token

import jp.glory.oauth.practice.authorization.api.token.request.RefreshRequest
import jp.glory.oauth.practice.authorization.api.token.response.RefreshedTokenResponse
import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.spec.Scope
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import jp.glory.oauth.practice.authorization.spec.refres_token.RefreshToken
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import jp.glory.oauth.practice.authorization.store.RefreshTokenStore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/token/refresh")
class RefreshTokenApi(
    private val accessTokenStore: AccessTokenStore,
    private val refreshTokenStore: RefreshTokenStore
) : RegisterTokenBase(accessTokenStore, refreshTokenStore) {
    @PostMapping
    fun refresh(
        @RequestBody request: RefreshRequest,
    ): ResponseEntity<out Any> =
        request
            .validate()
            .flatMap { findRefreshToken(request.refreshToken) }
            .flatMap { findBeforeRefreshTokenInfo(it.value) }
            .flatMap { registerNewToken(request.toScope(), it) }
            .flatMap { info ->
                saveRefreshToken(info.after.refreshToken)
                    .map { info }
            }
            .flatMap { info ->
                revokeAccessToken(info.before.accessToken)
                    .map { info }
            }
            .mapBoth(
                right = { createRefreshedResponse(it.after) },
                left = { createErrorResponse(it) }
            )

    private fun findBeforeRefreshTokenInfo(
        refreshTokenValue: String
    ): Either<Errors, TokenInfo> =
        findRefreshToken(refreshTokenValue)
            .flatMap { refreshToken ->
                findAccessToken(refreshToken.accessTokenValue)
                    .map {
                        TokenInfo(
                            refreshToken = refreshToken,
                            accessToken = it
                        )
                    }
            }

    private fun findRefreshToken(
        refreshTokenValue: String
    ): Either<Errors, RefreshToken> =
        refreshTokenStore
            .findByToken(refreshTokenValue)
            .mapBoth(
                right = {
                    if (it != null) {
                        Right(it)
                    } else {
                        createInvalidError("Refresh token is not found.")
                    }
                },
                left = { createServerError("Find refresh token is failed.") }
            )

    private fun findAccessToken(
        accessTokenValue: String
    ): Either<Errors, AccessToken> =
        accessTokenStore.findByTokenValue(accessTokenValue)
            .mapBoth(
                right = {
                    if (it != null) {
                        Right(it)
                    } else {
                        createInvalidError("Access token is not found.")
                    }
                },
                left = { createServerError("Find access token is failed.") }
            )

    private fun saveRefreshToken(
        refreshToken: RefreshToken
    ): Either<Errors, Unit> =
        refreshTokenStore.save(refreshToken)
            .mapBoth(
                right = { Right(Unit) },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Save refresh token is failed."
                        )
                    )
                }
            )

    private fun registerNewToken(
        newScope: List<Scope>,
        before: TokenInfo
    ): Either<Errors, TokenDiffInfo> =
        registerToken(newScope)
            .map {
                TokenDiffInfo(
                    before,
                    createNewTokenInfo(
                        refreshToken = before.refreshToken,
                        accessToken = it
                    )
                )
            }

    private fun createNewTokenInfo(
        refreshToken: RefreshToken,
        accessToken: AccessToken
    ): TokenInfo = TokenInfo(
        refreshToken = RefreshToken(
            value = refreshToken.value,
            accessTokenValue = accessToken.value
        ),
        accessToken = accessToken
    )

    fun revokeAccessToken(
        accessToken: AccessToken
    ): Either<Errors, Unit> =
        accessTokenStore.revokeToken(accessToken)
            .mapBoth(
                right = { Right(Unit) },
                left = {
                    Left(
                        Errors(
                            type = ErrorType.ServerError,
                            message = "Revoke access token is failed."
                        )
                    )
                }
            )

    private fun createRefreshedResponse(
        info: TokenInfo
    ): ResponseEntity<RefreshedTokenResponse> =
        RefreshedTokenResponse(
            accessToken = info.accessToken.value,
            scope = info.accessToken.scopes.map { it.name },
        )
            .let { ResponseEntity.ok(it) }

    private fun createInvalidError(
        message: String
    ): Left<Errors> =
        Left(
            Errors(
                type = ErrorType.InvalidRequest,
                message = message
            )
        )

    private fun createServerError(
        message: String
    ): Left<Errors> =
        Left(
            Errors(
                type = ErrorType.ServerError,
                message = message
            )
        )

    private class TokenInfo(
        val accessToken: AccessToken,
        val refreshToken: RefreshToken
    )

    private class TokenDiffInfo(
        val before: TokenInfo,
        val after: TokenInfo,
    )
}