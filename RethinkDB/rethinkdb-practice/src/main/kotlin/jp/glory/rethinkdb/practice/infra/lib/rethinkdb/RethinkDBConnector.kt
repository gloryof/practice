package jp.glory.rethinkdb.practice.infra.lib.rethinkdb

import com.rethinkdb.RethinkDB
import com.rethinkdb.gen.ast.ReqlExpr
import com.rethinkdb.gen.ast.Table
import com.rethinkdb.net.Connection
import com.rethinkdb.net.Result
import jp.glory.rethinkdb.practice.infra.store.dao.NotificationTable
import jp.glory.rethinkdb.practice.infra.store.dao.TodoTable

class RethinkDBConnector(
    val rethinkDB: RethinkDB,
    hostName: String,
    port: Int,
    private val dbName: String
) {
    private val connection = rethinkDB
        .connection()
        .hostname(hostName)
        .port(port)
        .connect()

    init {
        createDbIfNotExist(
            rethinkDB = rethinkDB,
            connection = connection,
            dbName = dbName
        )
        listOf(
            TodoTable.name,
            NotificationTable.name
        )
            .forEach {
                createTableIfNotExist(
                    rethinkDB = rethinkDB,
                    connection = connection,
                    dbName = dbName,
                    tableName = it
                )
            }
    }

    fun getTodo(): Table = rethinkDB.db(dbName).table(TodoTable.name)
    fun getNotification(): Table = rethinkDB.db(dbName).table(NotificationTable.name)

    fun run(expr: ReqlExpr): Result<Map<*, *>> =
        expr.run(connection, Map::class.java)

}

private fun createDbIfNotExist(
    rethinkDB: RethinkDB,
    connection: Connection,
    dbName: String
) {
    val dbList = rethinkDB.dbList().run(connection)
    val existDb =  dbList.toList().any { list ->
        (list as ArrayList<*>).any { it == dbName }
    }

    if (!existDb) {
        rethinkDB.dbCreate(dbName).run(connection)
    }
}


private fun createTableIfNotExist(
    rethinkDB: RethinkDB,
    connection: Connection,
    dbName: String,
    tableName: String
) {
    val tableList = rethinkDB.db(dbName).tableList().run(connection)

    val todoTableExist = tableList.any { list ->
        (list as ArrayList<*>).any { it == tableName }
    }
    if (!todoTableExist) {
        rethinkDB.db(dbName).tableCreate(tableName).run(connection)
    }
}