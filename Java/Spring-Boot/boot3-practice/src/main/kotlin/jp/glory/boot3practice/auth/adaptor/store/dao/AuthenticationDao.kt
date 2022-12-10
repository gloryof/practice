package jp.glory.boot3practice.auth.adaptor.store.dao

import jp.glory.boot3practice.base.adaptor.store.Dao

@Dao
class AuthenticationDao {
    private val auths = mutableMapOf<String, AuthenticationTable>()
        .also {
            val sampleData = AuthenticationTable(
                id = "test-user-id",
                password = "test-password"
            )
            it[sampleData.id] = sampleData
        }
    fun save(table: AuthenticationTable) = auths.put(table.id, table)
    fun findById(id: String): AuthenticationTable? = auths[id]
}

data class AuthenticationTable(
    val id: String,
    val password: String
)