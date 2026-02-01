package jp.glory.practice.boot.app.auth.data

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.jdbc.JdbcDatabase

class AuthDao(
    private val database: JdbcDatabase
) {
    private val table = Meta.authRecord

    fun insert(record: AuthRecord) {
        database.runQuery {
            QueryDsl.insert(table).single(record)
        }
    }

    fun findById(loginId: String): AuthRecord? =
        database.runQuery {
            QueryDsl.from(table).where {
                table.loginId eq loginId
            }
                .singleOrNull()
        }
}
