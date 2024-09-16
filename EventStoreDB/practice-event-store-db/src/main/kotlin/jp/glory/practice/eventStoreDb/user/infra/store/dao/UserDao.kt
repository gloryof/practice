package jp.glory.practice.eventStoreDb.user.infra.store.dao

import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.EventStoreDbEventData
import org.springframework.stereotype.Component

@Component
class UserDao {
    companion object {
        private const val TEST_USER_ID = "test-user-id"
    }
    private val users = mutableMapOf<String, UserRecord>()
        .also {
            it[TEST_USER_ID] = UserRecord(
                userId = TEST_USER_ID,
                userName = "test-user-name",
                postalCode = "1234567",
                prefectureCode = "13",
                city = "大きい市区町村",
                street = "大きいマンション1号室",
                giftPoint = 1000u
            )
        }

    fun save(record: UserRecord) {
        users[record.userId] = record
    }

    fun findById(id: String): UserRecord? = users[id]
}

data class UserRecord(
    val userId: String,
    val userName: String,
    val postalCode: String,
    val prefectureCode: String,
    val city: String,
    val street: String,
    val giftPoint: UInt
)
