package jp.glory.practice.boot.app.auth.data

class AuthDao(
    private val table: MutableMap<String, AuthRecord> = mutableMapOf()
) {
    fun insert(record: AuthRecord) {
        table.put(record.loginId, record)
    }

    fun findById(loginId: String): AuthRecord? =
        table.filter { it.value.loginId == loginId }
            .firstNotNullOfOrNull {
                it.value
            }
}
