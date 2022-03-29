package jp.glory.ci.cd.practice.app.user.usecase

import com.github.michaelbull.result.*
import jp.glory.ci.cd.practice.app.base.usecase.UseCase
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseUnknownError
import jp.glory.ci.cd.practice.app.user.domain.model.*
import jp.glory.ci.cd.practice.app.user.domain.repository.UserRepository

@UseCase
class UpdateUser(
    private val repository: UserRepository
) {
    class Input(
        val userId: String,
        val givenName: String,
        val familyName: String
    )

    class Output(
        val userId: String
    )

    fun update(input: Input): Result<Output, UseCaseError> =
        convertToEvent(input)
            .flatMap { updateEvent(it) }
            .map { Output(it.value) }

    private fun convertToEvent(
        input: Input
    ): Result<UpdateUserEvent, UseCaseUnknownError> =
        kotlin.runCatching {
            UpdateUserEvent(
                userId = UserId(input.userId),
                givenName = GivenName(input.givenName),
                familyName = FamilyName(input.familyName)
            )
        }
            .fold(
                onSuccess = { Ok(it) },
                onFailure = { createError("Invalid argument", it) }
            )

    private fun updateEvent(
        event: UpdateUserEvent
    ): Result<UserId, UseCaseUnknownError> =
        repository.update(event)
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(UseCaseUnknownError(it)) }
            )

    private fun createError(
        message: String,
        cause: Throwable
    ): Err<UseCaseUnknownError> =
        Err(
            UseCaseUnknownError(
                errorMessage = message,
                cause = cause
            )
        )
}