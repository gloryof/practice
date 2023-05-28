package jp.glory.detekt.practice.lib.store.dao

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TodoDao {
    private val todos = mutableMapOf<String, TodoRecord>()

    fun findById(id: String): TodoRecord? = todos[id]
    fun findAll(): List<TodoRecord> = todos.values.toList()

    fun register(todoRecord: TodoRecord) {
        todos[todoRecord.id] = todoRecord
    }

    fun update(todoRecord: TodoRecord) {
        todos[todoRecord.id] = todoRecord
    }

    fun delete(id: String) = todos.remove(id)
}

data class TodoRecord(
    val id: String,
    val title: String,
    val deadLine: LocalDate,
    val started: Boolean,
    val finished: Boolean
)
