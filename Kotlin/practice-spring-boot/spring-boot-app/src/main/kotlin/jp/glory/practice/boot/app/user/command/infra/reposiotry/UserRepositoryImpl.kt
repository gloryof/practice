package jp.glory.practice.boot.app.user.command.infra.reposiotry

import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.user.command.domain.repository.UserRepository

class UserRepositoryImpl(
    private val authDao: AuthDao
) : UserRepository {
    override fun existLoginId(loginId: LoginId): Boolean =
        authDao.findById(loginId.value) != null
}
