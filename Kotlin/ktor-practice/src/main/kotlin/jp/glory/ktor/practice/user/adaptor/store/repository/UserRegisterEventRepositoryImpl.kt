package jp.glory.ktor.practice.user.adaptor.store.repository

import jp.glory.ktor.practice.auth.adaptor.store.dao.AuthenticationDao
import jp.glory.ktor.practice.auth.adaptor.store.dao.AuthenticationTable
import jp.glory.ktor.practice.user.adaptor.store.dao.UserDao
import jp.glory.ktor.practice.user.adaptor.store.dao.UserTable
import jp.glory.ktor.practice.user.domain.event.UserRegisterEvent
import jp.glory.ktor.practice.user.domain.repository.UserRegisterEventRepository

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
