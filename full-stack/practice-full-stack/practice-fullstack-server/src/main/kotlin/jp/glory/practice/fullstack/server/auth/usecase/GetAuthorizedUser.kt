package jp.glory.practice.fullstack.server.auth.usecase

import jp.glory.practice.fullstack.server.auth.domain.AuthorizedUserRepository

class GetAuthorizedUser(
    private val repository: AuthorizedUserRepository,
) {
    fun getUserByToken(input: Input): Output? =
        repository.findByToken(input.token)
            ?.let {
                Output(
                    userId = it.userId.value,
                    token = input.token
                )
            }

    class Input(
        val token: String
    )
    class Output(
        val userId: String,
        val token: String,
    )
}