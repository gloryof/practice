package jp.glory.practice.boot.app.auth.data

class TokenDao(
    private val table: MutableMap<String, TokenRecord> = mutableMapOf()
) {
    fun insert(record: TokenRecord) {
        table.put(record.userId, record)
    }
}
