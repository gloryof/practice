package jp.glory.boot3practice.user.adaptor.store.repository

import jp.glory.boot3practice.auth.adaptor.store.dao.AuthenticationDao
import jp.glory.boot3practice.auth.adaptor.store.dao.AuthenticationTable
import jp.glory.boot3practice.user.adaptor.store.dao.UserDao
import jp.glory.boot3practice.user.adaptor.store.dao.UserTable
import jp.glory.boot3practice.user.domain.event.UserRegisterEvent
import jp.glory.boot3practice.user.domain.repository.UserRegisterEventRepository
import org.springframework.stereotype.Repository

@Repository
class UserRegisterEventRepositoryImpl(
    private val userDao: UserDao,
    private val authenticationDao: AuthenticationDao
) : UserRegisterEventRepository {
    override fun save(event: UserRegisterEvent) {
        userDao.save(
            UserTable(
                id = event.id.value,
                name = event.name.value,
                birthDay = event.birthDay.value
            )
        )
        authenticationDao.save(
            AuthenticationTable(
                id = event.id.value,
                password = event.password.value
            )
        )
    }
}