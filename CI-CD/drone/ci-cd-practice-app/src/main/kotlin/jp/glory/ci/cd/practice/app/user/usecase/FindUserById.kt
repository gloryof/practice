package jp.glory.ci.cd.practice.app.user.usecase

import com.github.michaelbull.result.*
import jp.glory.ci.cd.practice.app.base.usecase.UseCase
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseNotFoundError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseUnknownError
import jp.glory.ci.cd.practice.app.user.domain.model.*
import jp.glory.ci.cd.practice.app.user.domain.repository.UserRepository

@UseCase
class FindUserById(
    private val repository: UserRepository
) {
    class Input(
        val userId: String
    )

    class Output(
        val userId: String,
        val givenName: String,
        val familyName: String
    )

    fun find(input: Input): Result<Output, UseCaseError> =
        findById(UserId(input.userId))
            .flatMap { checkExist(it) }
            .map { mapToOutput(it) }

    private fun findById(
        userId: UserId
    ): Result<User?, UseCaseUnknownError> =
        repository.findById(userId)
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(UseCaseUnknownError(it)) }
            )

    private fun checkExist(
        user: User?
    ): Result<User, UseCaseNotFoundError> =
        user
            ?.let { Ok(it) }
            ?: Err(UseCaseNotFoundError("User"))

    private fun mapToOutput(
        user: User
    ): Output =
        Output(
            userId = user.userId.value,
            givenName = user.givenName.value,
            familyName = user.familyName.value
        )
}