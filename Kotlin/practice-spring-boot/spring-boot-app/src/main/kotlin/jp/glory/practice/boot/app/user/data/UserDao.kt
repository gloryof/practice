package jp.glory.practice.boot.app.user.data

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.jdbc.JdbcDatabase

class UserDao(
    private val database: JdbcDatabase
) {
    private val table = Meta.userRecord

    fun insert(record: UserRecord) {
        database.runQuery {
            QueryDsl.insert(table).single(record)
        }
    }

    fun findById(userId: String): UserRecord? =
        database.runQuery {
            QueryDsl.from(table).where {
                table.userId eq userId
            }
                .singleOrNull()
        }
}
