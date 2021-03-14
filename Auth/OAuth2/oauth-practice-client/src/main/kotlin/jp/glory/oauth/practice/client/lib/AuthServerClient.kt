package jp.glory.oauth.practice.client.lib

import com.fasterxml.jackson.annotation.JsonProperty
import jp.glory.oauth.practice.client.base.Either
import jp.glory.oauth.practice.client.base.Right
import jp.glory.oauth.practice.client.config.AuthServerConfig
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

interface AuthServerClient {

    fun generateTokenByCode(authCode: String): Either<Unit, TokenResponse>

    data class TokenResponse(
        val accessToken: String,
        val tokenType: String,
        val expiresIn: Long,
        val refreshToken: String,
        val scope: List<String>,
        val userId: String
    )
}

@Component
class AuthServerClientImpl : AuthServerClient {
    override fun generateTokenByCode(authCode: String): Either<Unit, AuthServerClient.TokenResponse> =
        TokenJsonResponse(
            accessToken = UUID.randomUUID().toString(),
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = UUID.randomUUID().toString(),
            scope = listOf("read", "write"),
            userId = "test-user-id"
        )
            .let {
                AuthServerClient.TokenResponse(
                    accessToken = it.accessToken,
                    tokenType = it.tokenType,
                    expiresIn = it.expiresIn,
                    refreshToken = it.refreshToken,
                    scope = it.scope,
                    userId = it.userId
                )
            }
            .let { Right(it) }

    data class TokenJsonResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("expires_in") val expiresIn: Long,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("scope") val scope: List<String>,
        @JsonProperty("user_id") val userId: String
    )
}