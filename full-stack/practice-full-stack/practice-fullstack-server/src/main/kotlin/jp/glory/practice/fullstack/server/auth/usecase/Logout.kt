package jp.glory.practice.fullstack.server.auth.usecase

import jp.glory.practice.fullstack.server.auth.domain.AuthorizedEventHandler
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedUserRepository

class Logout(
    private val handler: AuthorizedEventHandler,
    private val repository: AuthorizedUserRepository
) {
    fun logout(
        input: Input
    ) {
        repository
            .findByToken(input.token)
            ?.let { it.revokeToken() }
            ?.also { handler.revoke(it) }
    }

    class Input(
        val token: String,
    )
}