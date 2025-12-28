package jp.glory.practice.boot.app.user.command.domain.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.exception.DomainSpecErrorType
import jp.glory.practice.boot.app.user.command.domain.model.LoginId
import jp.glory.practice.boot.app.user.command.domain.repository.UserRepository

class UserService(
    private val repository: UserRepository
) {
    fun validateExistLogin(loginId: LoginId): Result<Unit, DomainErrors> =
        when (repository.existLoginId(loginId)) {
            false -> Ok(Unit)
            true -> Err(
                DomainErrors(
                    specErrors = listOf(DomainSpecErrorType.USER_ID_ALREADY_EXIST)
                )
            )
        }
}
