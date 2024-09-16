package jp.glory.practice.eventStoreDb.user.infra.repository

import jp.glory.practice.eventStoreDb.user.domain.model.Address
import jp.glory.practice.eventStoreDb.user.domain.model.City
import jp.glory.practice.eventStoreDb.user.domain.model.GiftPoint
import jp.glory.practice.eventStoreDb.user.domain.model.PostalCode
import jp.glory.practice.eventStoreDb.user.domain.model.Prefecture
import jp.glory.practice.eventStoreDb.user.domain.model.Street
import jp.glory.practice.eventStoreDb.user.domain.model.User
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.model.UserName
import jp.glory.practice.eventStoreDb.user.domain.repository.UserRepository
import jp.glory.practice.eventStoreDb.user.infra.store.dao.UserDao
import jp.glory.practice.eventStoreDb.user.infra.store.dao.UserRecord
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun findById(userId: UserId): User? =
        userDao.findById(userId.value)
            ?.let { toDomain(it) }

    private fun toDomain(userData: UserRecord): User =
        User(
            userId = UserId.fromString(userData.userId),
            name = UserName(userData.userName),
            address = Address(
                postalCode = PostalCode(userData.postalCode),
                prefecture = Prefecture.fromCode(userData.prefectureCode),
                city = City(userData.city),
                street = Street(userData.street)
            ),
            giftPoint = GiftPoint(userData.giftPoint)
        )
}