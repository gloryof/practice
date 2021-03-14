package jp.glory.oauth.practice.authorization.api

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/token")
class TokenApi {
    @PostMapping("/code")
    fun generateByCode(
        @RequestBody request: CodeRequest,
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

    data class CodeRequest(
        @JsonProperty("grant_type") val grantType: String,
        @JsonProperty("redirect_uri") val redirectUri: String,
        @JsonProperty("client_id") val clientId: String
    )

    data class OwnerRequest(
        @JsonProperty("grant_type") val grantType: String,
        @JsonProperty("scope") val scope: String,
    )

    data class ClientRequest(
        @JsonProperty("grant_type") val grantType: String,
        @JsonProperty("scope") val scope: String,
    )

    data class RefreshRequest(
        @JsonProperty("grant_type") val grantType: String,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("scope") val scope: String,
    )

    data class TokenResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("expires_in") val expiresIn: Long,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("scope") val scope: List<String>,
        @JsonProperty("user_id") val userId: String,
    )

    data class RefreshedTokenResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("scope") val scope: List<String>,
    )
}