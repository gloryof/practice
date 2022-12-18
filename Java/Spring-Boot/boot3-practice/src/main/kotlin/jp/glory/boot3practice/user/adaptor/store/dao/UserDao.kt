package jp.glory.boot3practice.user.adaptor.store.dao

import jp.glory.boot3practice.base.adaptor.store.Dao
import java.time.LocalDate

@Dao
class UserDao {
    private val sampleUser = UserTable(
        id = "test-user-id",
        name = "sample-user",
        birthDay = LocalDate.of(1986, 12, 17)
    )
    private val users = mutableMapOf<String, UserTable>()
        .apply { put(sampleUser.id, sampleUser) }
    fun save(table: UserTable) = users.put(table.id, table)
    fun findAll(): List<UserTable> = users.map { it.value }.toList()
    fun findById(id: String): UserTable? = users[id]
}

data class UserTable(
    val id: String,
    val name: String,
    val birthDay: LocalDate
)