package jp.glory.practice.boot.app.auth.command.domain.model

import java.time.Clock
import java.time.OffsetDateTime
import java.util.UUID

class AuthToken private constructor(
    val value: String,
    val expiresAt: OffsetDateTime
) {
    companion object {
        fun issue(clock: Clock): AuthToken = AuthToken(
            value = UUID.randomUUID().toString(),
            expiresAt = OffsetDateTime.now(clock).plusHours(3)
        )
    }
}
