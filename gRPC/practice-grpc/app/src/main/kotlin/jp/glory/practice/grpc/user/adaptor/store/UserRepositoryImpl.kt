package jp.glory.practice.grpc.user.adaptor.store

import jp.glory.practice.grpc.user.adaptor.store.db.UserDao
import jp.glory.practice.grpc.user.domain.model.User
import jp.glory.practice.grpc.user.domain.model.UserId
import jp.glory.practice.grpc.user.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun findById(id: UserId): User? =
        userDao.findById(id.value)
            ?.let { it.toDomain() }

    override fun findAllUsers(): List<User> =
        userDao.findAll()
            .map { it.toDomain() }
}