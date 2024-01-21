package jp.glory.practice.fullstack.server.base.adaptor.store

import org.springframework.stereotype.Component

@Component
class AuthDao {
    private val auths = mutableMapOf<String, AuthRecord>()

    fun findById(value: String): AuthRecord? = auths[value]
    fun save(record: AuthRecord) {
        auths[record.userId] = record
    }
}


class AuthRecord(
    val userId: String,
    val password: String,
)