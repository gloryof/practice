package jp.glory.oauth.practice.authorization.spec.auth_code

import jp.glory.oauth.practice.authorization.spec.Scope
import java.time.LocalDateTime
import java.util.*

data class AuthCode(
    val value: String,
    val scopes: List<Scope>,
    val expiresAt: LocalDateTime
) {
    companion object {
        fun generate(
            scopes: List<Scope>
        ): AuthCode =
            AuthCode(
                value = UUID.randomUUID().toString(),
                scopes = scopes,
                expiresAt = LocalDateTime.now().plusMinutes(5),
            )
    }
}