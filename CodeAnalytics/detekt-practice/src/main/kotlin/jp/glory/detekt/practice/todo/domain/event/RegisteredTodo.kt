package jp.glory.detekt.practice.todo.domain.event

import java.time.LocalDate

class RegisteredTodo(
    val id: String,
    val title: String,
    val deadLine: LocalDate
) {
    init {
        val today = LocalDate.now()
        require(deadLine.isAfter(today) || deadLine == today)
    }
}