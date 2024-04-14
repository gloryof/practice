package jp.glory.practice.fullstack.server.base.adaptor.store

class AuthDao {
    private val auths = mutableMapOf<String, AuthRecord>()
        .also {
            it["test-user-id"] = AuthRecord(
                userId = "test-user-id",
                password = "test-password"
            )
        }

    fun findById(value: String): AuthRecord? = auths[value]
    fun save(record: AuthRecord) {
        auths[record.userId] = record
    }
}


class AuthRecord(
    val userId: String,
    val password: String,
)