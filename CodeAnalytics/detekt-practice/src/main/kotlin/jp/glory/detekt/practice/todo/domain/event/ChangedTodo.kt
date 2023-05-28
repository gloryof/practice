package jp.glory.detekt.practice.todo.domain.event

import java.time.LocalDate

class ChangedTodo(
    val id: String,
    val title: String,
    val deadLine: LocalDate,
)
