package jp.glory.oauth.practice.authorization.api.token

import com.fasterxml.jackson.annotation.JsonProperty
import jp.glory.oauth.practice.authorization.api.token.response.ErrorResponse
import jp.glory.oauth.practice.authorization.base.ServerError
import jp.glory.oauth.practice.authorization.base.map
import jp.glory.oauth.practice.authorization.base.mapBoth
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/introspect")
class IntrospectApi(
    private val tokenStore: AccessTokenStore
) {

    @PostMapping
    fun introspect(
        @RequestBody request: Request
    ): ResponseEntity<out Any> =
        tokenStore
            .findByTokenValue(request.token)
            .map {
                if (it != null) {
                    mapToResponse(it)
                } else {
                    notFoundResponse()
                }
            }
            .mapBoth(
                right = { ResponseEntity.ok(it) },
                left = { createErrorResponse(it) }
            )

    private fun createErrorResponse(
        error: ServerError
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    error = RegisterTokenBase.ErrorType.ServerError.value,
                    errorDescription = error.message
                )
            )
    }
    private fun mapToResponse(token: AccessToken): Response =
        Response(
            active = true,
            scope = token.scopes.map { it.name },
            clientId = null,
            username = null,
            tokenType = "Bearer",
            exp = null,
            iat = null,
            nbf = null,
            sub = null,
            aud = null,
            iss = null,
            jti = UUID.randomUUID().toString(),
        )

    private fun notFoundResponse(): Response =
        Response(
            active = false
        )

    data class Request(
        val token: String,
        @JsonProperty("token_type_hint") val typeHint: String
    )

    data class Response(
        val active: Boolean,
        val scope: List<String> = emptyList(),
        @JsonProperty("client_id") val clientId: String? = null,
        val username: String? = null,
        @JsonProperty("token_type") val tokenType: String? = null,
        val exp: Long? = null,
        val iat: Long? = null,
        val nbf: Long? = null,
        val sub: String? = null,
        val aud: String? = null,
        val iss: String? = null,
        val jti: String? = null
    )
}