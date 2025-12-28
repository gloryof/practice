package jp.glory.practice.boot.app.auth.data

class AuthDao(
    private val table: MutableMap<String, AuthRecord> = mutableMapOf()
) {
    fun insert(record: AuthRecord) {
        table.put(record.loginId, record)
    }
}
