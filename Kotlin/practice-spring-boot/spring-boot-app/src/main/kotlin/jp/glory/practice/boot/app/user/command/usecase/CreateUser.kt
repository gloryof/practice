package jp.glory.practice.boot.app.user.command.usecase

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.base.command.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.user.command.domain.event.UserCreated
import jp.glory.practice.boot.app.user.command.domain.event.UserEventHandler
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.command.domain.service.UserService
import java.time.Clock
import java.time.LocalDate

class CreateUser(
    private val clock: Clock,
    private val userService: UserService,
    private val userEventHandler: UserEventHandler,
    private val userIdGenerator: UserIdGenerator
) {
    fun createUser(input: Input): Result<String, UsecaseErrors> =
        convertLoginId(input.loginId)
            .flatMap { userService.validateExistLogin(it) }
            .flatMap { input.toDomainEvent(clock, userIdGenerator) }
            .flatMap { Ok(handleEvent(it)) }
            .mapError { UsecaseErrors.fromDomainError(it) }

    private fun convertLoginId(loginId: String): Result<LoginId, DomainErrors> =
        LoginId.of(loginId)
            .mapError {
                DomainErrors(itemErrors = listOf(it))
            }

    private fun handleEvent(event: UserCreated): String {
        userEventHandler.handleUserCreated(event)
        return event.userId.value
    }


    data class Input(
        val loginId: String,
        val userName: String,
        val password: String,
        val birthday: LocalDate
    ) {
        fun toDomainEvent(
            clock: Clock,
            userIdGenerator: UserIdGenerator
        ): Result<UserCreated, DomainErrors> =
            UserCreated.create(
                inputLoginId = loginId,
                inputUserName = userName,
                inputPassword = password,
                inputBirthday = birthday,
                today = LocalDate.now(clock),
                userIdGenerator = userIdGenerator
            )
    }
}
