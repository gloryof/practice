package jp.glory.oauth.practice.authorization.api

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/introspect")
class IntrospectApi {

    @PostMapping
    fun introspect(
        @RequestBody request: Request
    ): ResponseEntity<Response> =
        ResponseEntity.ok(
            Response(
                active = true,
                scope = listOf("read", "write"),
                clientId = UUID.randomUUID().toString(),
                username = "test-user",
                tokenType = "Bearer",
                exp = 1615096637,
                iat = 1615096637,
                nbf = 1615096637,
                sub = "test-subject",
                aud = "audience-name",
                iss = "issuer-name",
                jti = UUID.randomUUID().toString(),
            )
        )

    data class Request(
        val token: String,
        @JsonProperty("token_type_hint") val typeHint: String
    )

    data class Response(
        val active: Boolean,
        val scope: List<String>,
        @JsonProperty("client_id") val clientId: String,
        val username: String,
        @JsonProperty("token_type") val tokenType: String,
        val exp: Long,
        val iat: Long,
        val nbf: Long,
        val sub: String,
        val aud: String,
        val iss: String,
        val jti: String
    )
}