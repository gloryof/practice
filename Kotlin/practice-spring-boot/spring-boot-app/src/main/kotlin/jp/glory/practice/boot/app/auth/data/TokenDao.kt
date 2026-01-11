package jp.glory.practice.boot.app.auth.data

class TokenDao(
    private val table: MutableMap<String, TokenRecord> = mutableMapOf()
) {
    fun insert(record: TokenRecord) {
        table.put(record.userId, record)
    }

    fun findByToken(token: String): TokenRecord? =
        table.filter { it.value.token == token }
            .firstNotNullOfOrNull { it.value }
}
