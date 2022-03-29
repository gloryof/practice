package jp.glory.ci.cd.practice.app.user.usecase

import com.github.michaelbull.result.*
import jp.glory.ci.cd.practice.app.base.usecase.UseCase
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseUnknownError
import jp.glory.ci.cd.practice.app.user.domain.model.*
import jp.glory.ci.cd.practice.app.user.domain.repository.UserRepository

@UseCase
class RegisterUser(
    private val repository: UserRepository,
    private val userIdGenerator: UserIdGenerator
) {
    class Input(
        val givenName: String,
        val familyName: String,
        val password: String
    )

    class Output(
        val userId: String
    )

    fun register(input: Input): Result<Output, UseCaseError> =
        convertToEvent(input)
            .flatMap { registerEvent(it) }
            .map { Output(it.value) }

    private fun convertToEvent(
        input: Input
    ): Result<RegisterUserEvent, UseCaseUnknownError> =
        kotlin.runCatching {
            RegisterUserEvent(
                userId = userIdGenerator.generate(),
                givenName = GivenName(input.givenName),
                familyName = FamilyName(input.familyName),
                password = Password(input.password)
            )
        }
            .fold(
                onSuccess = { Ok(it) },
                onFailure = {
                    Err(
                        UseCaseUnknownError(
                            errorMessage = "Invalid argument",
                            cause = it
                        )
                    )
                }
            )

    private fun registerEvent(
        event: RegisterUserEvent
    ): Result<UserId, UseCaseUnknownError> =
        repository.register(event)
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(UseCaseUnknownError(it)) }
            )
}