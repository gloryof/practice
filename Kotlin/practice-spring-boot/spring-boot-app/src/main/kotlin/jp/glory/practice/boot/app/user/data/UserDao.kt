package jp.glory.practice.boot.app.user.data

class UserDao(
    private val table: MutableMap<String, UserRecord> = mutableMapOf()
) {
    fun insert(record: UserRecord) {
        table.put(record.userId, record)
    }

    fun findByLoginId(loginId: String): UserRecord? =
        table.filter { it.value.loginId == loginId }
            .firstNotNullOfOrNull {
                it.value
            }
}
