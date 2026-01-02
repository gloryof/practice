package jp.glory.practice.boot.app.auth.command.infra.repository

import com.github.michaelbull.result.get
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.Password
import jp.glory.practice.boot.app.auth.command.domain.model.UserCredential
import jp.glory.practice.boot.app.auth.command.domain.repository.UserCredentialRepository
import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.auth.data.AuthRecord
import jp.glory.practice.boot.app.user.command.domain.model.UserId

class UserCredentialRepositoryImpl(
    private val authDao: AuthDao
) : UserCredentialRepository {
    override fun findByLoginId(loginId: LoginId): UserCredential? =
        authDao.findById(loginId.value)
            ?.let { toUserCredential(it) }

    private fun toUserCredential(record: AuthRecord): UserCredential =
        UserCredential(
            loginId = requireNotNull(LoginId.of(record.loginId).get()),
            userId = UserId(record.userId),
            password = requireNotNull(Password.of(record.password).get()),
        )

}
