package jp.glory.oauth.practice.authorization.spec.access_token

import jp.glory.oauth.practice.authorization.spec.Scope
import java.time.LocalDateTime
import java.util.*

class AccessToken(
    val value: String,
    val type: String,
    val scopes: List<Scope>,
    val expiresAt: LocalDateTime
) {
    companion object {
        fun generate(
            scopes: List<Scope>
        ): AccessToken =
            AccessToken(
                value = UUID.randomUUID().toString(),
                type = "Bearer",
                scopes = scopes,
                expiresAt = LocalDateTime.now().plusHours(24),
            )
    }
}