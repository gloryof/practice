package jp.glory.boot3practice.user.adaptor.store.repository

import jp.glory.boot3practice.user.adaptor.store.dao.UserDao
import jp.glory.boot3practice.user.domain.model.BirthDay
import jp.glory.boot3practice.user.domain.model.User
import jp.glory.boot3practice.user.domain.model.UserName
import jp.glory.boot3practice.user.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun findAllUsers(): List<User> =
        userDao.findAll()
            .map {
                User(
                    name = UserName(it.name),
                    birthDay = BirthDay(it.birthDay)
                )
            }
}