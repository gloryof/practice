package jp.glory.practice.fullstack.server.base.adaptor.store

import java.time.LocalDate

class UserDao {
    private val users = mutableMapOf<String, UserRecord>()

    fun findById(value: String): UserRecord? = users[value]
    fun save(record: UserRecord) {
        users[record.id] = record
    }
}


class UserRecord(
    val id: String,
    val name: String,
    val birthday: LocalDate
)