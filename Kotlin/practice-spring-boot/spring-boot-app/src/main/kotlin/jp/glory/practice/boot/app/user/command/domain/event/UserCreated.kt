package jp.glory.practice.boot.app.user.command.domain.event

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.zipOrAccumulate
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.Password
import jp.glory.practice.boot.app.base.command.domain.exception.DomainErrors
import jp.glory.practice.boot.app.user.command.domain.model.Birthday
import jp.glory.practice.boot.app.user.command.domain.model.UserId
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.command.domain.model.UserName
import java.time.LocalDate


class UserCreated private constructor(
    val userId: UserId,
    val loginId: LoginId,
    val userName: UserName,
    val birthday: Birthday,
    val password: Password
) {
    companion object {
        fun create(
            inputLoginId: String,
            inputUserName: String,
            inputPassword: String,
            inputBirthday: LocalDate,
            today: LocalDate,
            userIdGenerator: UserIdGenerator
        ): Result<UserCreated, DomainErrors> {
            val result = zipOrAccumulate(
                { LoginId.of(inputLoginId) },
                { UserName.of(inputUserName) },
                { Password.of(inputPassword) },
                { Birthday.of(inputBirthday, today) }
            ) { loginId, userName, password, birthday ->
                UserCreated(
                    userId = userIdGenerator.issueNewId(),
                    loginId = loginId,
                    userName = userName,
                    password = password,
                    birthday = birthday
                )
            }

            return result.mapError { DomainErrors(itemErrors = it) }
        }
    }
}
