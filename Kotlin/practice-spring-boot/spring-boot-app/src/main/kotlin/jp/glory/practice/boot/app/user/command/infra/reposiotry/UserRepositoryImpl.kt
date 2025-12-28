package jp.glory.practice.boot.app.user.command.infra.reposiotry

import jp.glory.practice.boot.app.user.command.domain.model.LoginId
import jp.glory.practice.boot.app.user.command.domain.repository.UserRepository
import jp.glory.practice.boot.app.user.data.UserDao

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun existLoginId(loginId: LoginId): Boolean =
        userDao.findByLoginId(loginId.value) != null
}
