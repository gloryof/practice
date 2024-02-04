package jp.glory.practice.fullstack.server.base.adaptor.store

class TokenDao {
    private val tokens = mutableMapOf<String, TokenRecord>()

    fun findByToken(tokenValue: String): TokenRecord? = tokens[tokenValue]
    fun save(record: TokenRecord) {
        tokens[record.token] = record
    }
    fun delete(tokenValue: String) {
        tokens.remove(tokenValue)
    }
}


class TokenRecord(
    val token: String,
    val userId: String,
)