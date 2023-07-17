package jp.glory.ktor.practice.user.adaptor.store.repository

import jp.glory.ktor.practice.user.adaptor.store.dao.UserDao
import jp.glory.ktor.practice.user.adaptor.store.dao.UserTable
import jp.glory.ktor.practice.user.domain.model.BirthDay
import jp.glory.ktor.practice.user.domain.model.User
import jp.glory.ktor.practice.user.domain.model.UserId
import jp.glory.ktor.practice.user.domain.model.UserName
import jp.glory.ktor.practice.user.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun findAllUsers(): List<User> =
        userDao.findAll()
            .map { toUser(it) }

    override fun findById(id: UserId): User? =
        userDao.findById(id.value)
            ?.let { toUser(it) }

    private fun toUser(table: UserTable): User =
        User(
            name = UserName(table.name),
            birthDay = BirthDay(table.birthDay)
        )

}
