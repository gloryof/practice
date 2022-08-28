package jp.glory.pratice.test_container.db

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl {
    fun register(user: User) {
        transaction {
            UsersTable.insert {
                it[id] = user.id
                it[name] = user.name
                it[age] = user.age
            }
        }
    }

    fun findById(id: String): User? =
        transaction {
            UsersTable.select { UsersTable.id eq id }
                .firstOrNull()
                ?.let {
                    User(
                        id = it[UsersTable.id],
                        name = it[UsersTable.name],
                        age = it[UsersTable.age]
                    )
                }
        }
}

data class User(
    val id: String,
    val name: String,
    val age: Int
)