package jp.glory.rethinkdb.practice.infra.store.dao

import com.rethinkdb.gen.ast.Table
import jp.glory.rethinkdb.practice.infra.lib.rethinkdb.RethinkDBConnector
import org.springframework.stereotype.Component

@Component
class TodoDao(
    private val connector: RethinkDBConnector
) {
    fun findById(id: String): TodoRecord? =
        getTable()
            .filterById(id)
            .let { connector.run(it) }
            .let { result ->
                if (result.hasNext()) {
                    result.first()
                } else {
                    null
                }
            }
            ?.let { TodoTable.toRecord(it) }

    fun findAll(): List<TodoRecord> =
        getTable()
            .let { connector.run(it) }
            .let { result ->
                result.map { TodoTable.toRecord(it) }
            }

    fun register(todoRecord: TodoRecord) {
        val table = getTable()
        connector.rethinkDB
            .hashMap(TodoTable.Columns.TodoId.columnName, todoRecord.id)
            .with(TodoTable.Columns.Title.columnName, todoRecord.title)
            .with(TodoTable.Columns.DeadLine.columnName, todoRecord.deadLine)
            .with(TodoTable.Columns.Started.columnName, todoRecord.started)
            .with(TodoTable.Columns.Finished.columnName, todoRecord.finished)
            .let { table.insert(it) }
            .let { connector.run(it) }
    }

    fun update(todoRecord: TodoRecord) {
        val expression = connector.rethinkDB
            .hashMap(TodoTable.Columns.Title.columnName, todoRecord.title)
            .with(TodoTable.Columns.DeadLine.columnName, todoRecord.deadLine)
            .with(TodoTable.Columns.Started.columnName, todoRecord.started)
            .with(TodoTable.Columns.Finished.columnName, todoRecord.finished)
        getTable()
            .filterById(todoRecord.id)
            .update(expression)
            .let { connector.run(it) }
    }

    fun delete(id: String) {
        getTable()
            .filterById(id)
            .delete()
            .let { connector.run(it) }
    }

    private fun getTable(): Table =
        connector.getTodo()

    private fun Table.filterById(id: String) =
        this.filter { rows -> rows.g(TodoTable.Columns.TodoId.columnName).eq(id) }
}

object TodoTable {
    const val name = "todo"
    enum class Columns(val columnName: String) {
        TodoId("todoId"),
        Title("title"),
        DeadLine("deadLine"),
        Started("started"),
        Finished("finished")
    }
    fun toRecord(result: Map<*, *>): TodoRecord =
        TodoRecord(
            id = result[Columns.TodoId.columnName] as String,
            title = result[Columns.Title.columnName] as String,
            deadLine = result[Columns.DeadLine.columnName] as String,
            started = result[Columns.Started.columnName] as Boolean,
            finished = result[Columns.Finished.columnName] as Boolean,
        )

}

data class TodoRecord(
    val id: String,
    val title: String,
    val deadLine: String,
    val started: Boolean,
    val finished: Boolean
)