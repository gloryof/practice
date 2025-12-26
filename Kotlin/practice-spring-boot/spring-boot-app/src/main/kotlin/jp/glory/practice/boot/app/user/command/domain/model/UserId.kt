package jp.glory.practice.boot.app.user.command.domain.model

import java.util.UUID

@JvmInline
value class UserId(val value: String)

class UserIdGenerator {
    fun issueNewId(): UserId =
        UserId(UUID.randomUUID().toString())
}
