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
import jp.glory.practice.eventStoreDb.user.infra.store.UserDao
import jp.glory.practice.eventStoreDb.user.infra.store.UserRecord
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override fun findById(userId: UserId): User? =
        userDao.findById(userId.value)
            ?.let { toDomain(it) }

    private fun toDomain(userRecord: UserRecord): User =
        User(
            userId = UserId.fromString(userRecord.userId),
            name = UserName(userRecord.userName),
            address = Address(
                postalCode = PostalCode(userRecord.postalCode),
                prefecture = Prefecture.fromCode(userRecord.prefectureCode),
                city = City(userRecord.city),
                street = Street(userRecord.street)
            ),
            giftPoint = GiftPoint(userRecord.giftPoint)
        )
}