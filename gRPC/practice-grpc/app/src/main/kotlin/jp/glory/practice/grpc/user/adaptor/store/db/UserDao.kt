package jp.glory.practice.grpc.user.adaptor.store.db

import org.springframework.stereotype.Repository

@Repository
class UserDao {
    private val records = mutableMapOf<String, UserRecord>()
    fun save(record: UserRecord) {
        records[record.id] = record
    }

    fun findById(id: String): UserRecord? = records[id]
    fun findAll(): List<UserRecord> = records.values.toList()
}