package jp.glory.boot3practice.auth.adaptor.store.dao

import jp.glory.boot3practice.base.adaptor.store.Dao

@Dao
class TokenDao {
    private val tokens = mutableMapOf<String, TokenTable>()
    fun save(table: TokenTable) = tokens.put(table.tokenValue, table)
    fun find(tokenValue: String): TokenTable? = tokens[tokenValue]
}

data class TokenTable(
    val tokenValue: String,
    val id: String
)