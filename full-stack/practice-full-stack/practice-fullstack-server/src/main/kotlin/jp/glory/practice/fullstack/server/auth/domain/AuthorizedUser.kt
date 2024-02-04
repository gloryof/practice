package jp.glory.practice.fullstack.server.auth.domain

import java.util.UUID

class RegisteredUser(
    val userId: AuthUserId,
    val password: RegisteredPassword
) {
    fun authorize(
        inputPassword: String
    ): AuthorizedEvent? {
        if (inputPassword != password.value) {
            return null
        }

        return AuthorizedEvent(
            userId = userId,
            token = AuthorizedToken.create()
        )
    }
}

class AuthorizedUser(
    val userId: AuthUserId,
    private val authorizedToken: AuthorizedToken
) {
    fun revokeToken() = RevokeTokenEvent(authorizedToken)
}

class AuthorizedEvent(
    val userId: AuthUserId,
    val token: AuthorizedToken
)

class RevokeTokenEvent(
    val token: AuthorizedToken
)

@JvmInline
value class AuthUserId(val value: String) {
    init {
        assert(value.isNotBlank())
    }
}

@JvmInline
value class RegisteredPassword(val value: String) {
    init {
        assert(value.isNotBlank())
    }
}


@JvmInline
value class AuthorizedToken(val value: String) {
    companion object {
        fun create(): AuthorizedToken = AuthorizedToken(UUID.randomUUID().toString())
    }
    init {
        assert(value.isNotBlank())
    }
}

interface AuthorizedEventHandler {
    fun register(event: AuthorizedEvent)
    fun revoke(event: RevokeTokenEvent)

}

interface RegisteredUserRepository {
    fun findById(userId: AuthUserId): RegisteredUser?
}


interface AuthorizedUserRepository {
    fun findByToken(token: String): AuthorizedUser?
}
