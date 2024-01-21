package jp.glory.practice.fullstack.server.base.adaptor.store

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
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