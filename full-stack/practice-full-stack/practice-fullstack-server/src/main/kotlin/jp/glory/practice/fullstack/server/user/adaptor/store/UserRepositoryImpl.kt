package jp.glory.practice.fullstack.server.user.adaptor.store

import jp.glory.practice.fullstack.server.base.adaptor.store.UserDao
import jp.glory.practice.fullstack.server.user.domain.Birthday
import jp.glory.practice.fullstack.server.user.domain.User
import jp.glory.practice.fullstack.server.user.domain.UserId
import jp.glory.practice.fullstack.server.user.domain.UserName
import jp.glory.practice.fullstack.server.user.domain.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun findById(id: UserId): User? =
        userDao.findById(id.value)
            ?.let {
                User(
                    id = UserId(it.id),
                    name = UserName(it.name),
                    birthday = Birthday(it.birthday)
                )
            }
}