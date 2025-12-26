package jp.glory.practice.boot.app.user.data.auth

class AuthDao(
    private val table: MutableMap<String, AuthRecord> = mutableMapOf()
) {
    fun insert(record: AuthRecord) {
        table.put(record.loginId, record)
    }
}
