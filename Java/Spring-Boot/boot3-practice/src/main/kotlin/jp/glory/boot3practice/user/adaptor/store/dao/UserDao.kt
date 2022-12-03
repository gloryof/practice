package jp.glory.boot3practice.user.adaptor.store.dao

import jp.glory.boot3practice.base.adaptor.store.Dao
import java.time.LocalDate
import java.util.UUID

@Dao
class UserDao {
    private val sampleUser = UserTable(
        id = UUID.randomUUID().toString(),
        name = "sample-user",
        birthDay = LocalDate.now()
    )
    private val users = mutableMapOf<String, UserTable>()
        .apply { put(sampleUser.id, sampleUser) }
    fun save(table: UserTable) = users.put(table.id, table)
    fun findAll(): List<UserTable> = users.map { it.value }.toList()
}

data class UserTable(
    val id: String,
    val name: String,
    val birthDay: LocalDate
)