package jp.glory.boot3practice.auth.adaptor.store.dao

import jp.glory.boot3practice.base.adaptor.store.Dao

@Dao
class AuthenticationDao {
    private val auths = mutableMapOf<String, AuthenticationTable>()
    fun save(table: AuthenticationTable) = auths.put(table.id, table)
}

data class AuthenticationTable(
    val id: String,
    val password: String
)