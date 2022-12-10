package jp.glory.boot3practice.auth.domain.model

import jp.glory.boot3practice.auth.domain.event.InputPassword
import jp.glory.boot3practice.auth.domain.event.InputUserId
import jp.glory.boot3practice.auth.domain.event.UserAuthenticateEvent

class Credential(
    private val id: UserId,
    private val password: Password
) {
    fun authenticate(event: UserAuthenticateEvent): AuthenticatedUserId? =
        if (isMatch(event)) {
            AuthenticatedUserId(id)
        } else {
            null
        }

    private fun isMatch(event: UserAuthenticateEvent): Boolean =
        id.isMatch(event.id)
                && password.isMatch(event.password)
}

@JvmInline
value class AuthenticatedUserId(val userId: UserId) {
    fun getValue(): String = userId.value
}

@JvmInline
value class UserId(val value: String) {
    fun isMatch(input: InputUserId): Boolean = value == input.value
}

@JvmInline
value class Password(val value: String) {
    fun isMatch(input: InputPassword): Boolean = value == input.value
}