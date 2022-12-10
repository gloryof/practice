package jp.glory.boot3practice.auth.domain.model

import java.util.*

class Token(
    val userId: AuthenticatedUserId,
    val tokenValue: TokenValue
) {
    companion object {
        fun generate(userId: AuthenticatedUserId): Token =
            Token(
                userId = userId,
                tokenValue = TokenValue.generate()
            )
    }
}

@JvmInline
value  class TokenValue(val value: String) {
    companion object {
        fun generate(): TokenValue = TokenValue(UUID.randomUUID().toString())
    }
}