package jp.glory.practice.boot.app.auth.command.domain.model

import java.time.Clock
import java.time.OffsetTime
import java.util.UUID

class AuthToken private constructor(
    val value: String,
    val expiresAt: OffsetTime
) {
    companion object {
        fun issue(clock: Clock): AuthToken = AuthToken(
            value = UUID.randomUUID().toString(),
            expiresAt = OffsetTime.now(clock).plusHours(3)
        )
    }
}
