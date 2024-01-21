package jp.glory.practice.fullstack.server.auth.usecase

import jp.glory.practice.fullstack.server.auth.domain.AuthUserId
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedEventHandler
import jp.glory.practice.fullstack.server.auth.domain.RegisteredUserRepository
import jp.glory.practice.fullstack.server.base.usecase.UseCase

@UseCase
class Login(
    private val handler: AuthorizedEventHandler,
    private val repository: RegisteredUserRepository
) {
    fun login(
        input: Input
    ): Output =
        repository
            .findById(AuthUserId(input.userId))
            ?.let { it.authorize(input.password) }
            ?.also { handler.register(it) }
            ?.let { Output(it.token.value) }
            ?: throw IllegalStateException("Login is failed")

    class Input(
        val userId: String,
        val password: String
    )

    class Output(
        val token: String
    )
}