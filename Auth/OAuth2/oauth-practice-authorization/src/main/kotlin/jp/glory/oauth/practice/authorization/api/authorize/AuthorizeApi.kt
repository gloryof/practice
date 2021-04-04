package jp.glory.oauth.practice.authorization.api.authorize

import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.spec.*
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import jp.glory.oauth.practice.authorization.spec.access_token.AccessTokenRegisterResult
import jp.glory.oauth.practice.authorization.spec.auth_code.AuthCode
import jp.glory.oauth.practice.authorization.spec.auth_code.AuthCodeRegisterResult
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import jp.glory.oauth.practice.authorization.store.AuthCodeStore
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping("/api/authorize")
class AuthorizeApi(
    private val codeStore: AuthCodeStore,
    private val tokenStore: AccessTokenStore
) {

    @GetMapping("/code")
    fun authorizeByCode(
        @RequestParam("response_type") responseType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("scope") scope: String,
        @RequestParam("state")  state: String
    ): ResponseEntity<URI> {
        val maybeScope = toMaybeScopes(scope)

        return validateInput(responseType, clientId, maybeScope, "code")
            .map { maybeScope.filterNotNull() }
            .map { AuthCode.generate(it) }
            .flatMap { registerAuthCode(it, state) }
            .mapBoth(
                right = { createCodeSuccessUrl(redirectUri, it.code, it.state) },
                left = { createFailUrl(redirectUri, Errors.ServerError, state) }
            )
            .let {
                ResponseEntity.status(HttpStatus.FOUND)
                    .location(it)
                    .build()
            }
    }

    @GetMapping("/implicit")
    fun authorizeImplicit(
        @RequestParam("response_type") responseType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("scope") scope: String,
        @RequestParam("state")  state: String
    ): ResponseEntity<URI> {
        val maybeScope = toMaybeScopes(scope)

        return validateInput(responseType, clientId, maybeScope, "token")
            .map { maybeScope.filterNotNull() }
            .map { AccessToken.generate(it) }
            .flatMap { registerAccessToken(it, state) }
            .mapBoth(
                right = { createImplicitSuccessUrl(redirectUri, it.token, it.state) },
                left = { createFailUrl(redirectUri, Errors.ServerError, state) }
            )
            .let {
                ResponseEntity.status(HttpStatus.FOUND)
                    .location(it)
                    .build()
            }
    }

    private fun toMaybeScopes(
        values: String
    ): List<Scope?> =
        values
            .split(" ")
            .map { Scope.codeFrom(it) }

    private fun validateInput(
        responseType: String,
        clientId: String,
        scopes: List<Scope?>,
        expectedType: String
    ): Either<Errors, Unit> {
        if (GrantedClient.codeFrom(clientId) == null) {
            return Left(Errors.UnauthorizedClient)
        }

        if (responseType != expectedType) {
            return Left(Errors.InvalidRequest)
        }

        val existInvalidScope = scopes.any { it == null }
        if (existInvalidScope) {
            return Left(Errors.InvalidRequest)
        }

        return Right(Unit)
    }

    private fun registerAuthCode(
        code: AuthCode,
        state: String,
    ): Either<FatalError, AuthCodeRegisterResult> =
        codeStore.register(code, state)
            .map {
                AuthCodeRegisterResult(
                    code = code,
                    state = state
                )
            }

    private fun registerAccessToken(
        token: AccessToken,
        state: String
    ): Either<FatalError, AccessTokenRegisterResult> =
        tokenStore.register(token, state)
            .map {
                AccessTokenRegisterResult(
                    token = token,
                    state = state
                )
            }

    private fun createCodeSuccessUrl(
        redirectUri: String,
        code: AuthCode,
        state: String
    ): URI =
        URI.create("$redirectUri?code=${code.value}&state=$state")

    private fun createImplicitSuccessUrl(
        redirectUri: String,
        token: AccessToken,
        state: String
    ): URI {
        val expiresIn = ChronoUnit.SECONDS.between(
            LocalDateTime.now(),
            token.expiresAt
        )
        return URI.create("$redirectUri?token=${token.value}&expires_in=$expiresIn&state=$state")
    }

    private fun createFailUrl(
        redirectUri: String,
        errors: Errors,
        state: String
    ): URI =
        URI.create("$redirectUri?error=${errors.value}&state=$state")

    enum class Errors(val value: String) {
        InvalidRequest("invalid_request"),
        UnauthorizedClient("unauthorized_client"),
        ServerError("server_error")
    }
}