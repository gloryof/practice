package jp.glory.open_feature.practice.user.adaptor.store.repository

import jp.glory.open_feature.practice.user.adaptor.store.dao.UserDao
import jp.glory.open_feature.practice.user.adaptor.store.dao.UserTable
import jp.glory.open_feature.practice.user.domain.event.UserRegisterEvent
import jp.glory.open_feature.practice.user.domain.repository.UserRegisterEventRepository
import org.springframework.stereotype.Repository

@Repository
class UserRegisterEventRepositoryImpl(
    private val userDao: UserDao
) : UserRegisterEventRepository {
    override fun save(event: UserRegisterEvent) {
        userDao.save(
            UserTable(
                id = event.id.value,
                name = event.name.value,
                birthDay = event.birthDay.value
            )
        )
    }
}