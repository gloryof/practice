package jp.glory.kafka.practice.todo.domain.event

import java.time.LocalDate

class ChangedTodo(
    val id: String,
    val newTitle: String,
    val oldTile: String,
    val newDeadLine: LocalDate,
    val oldDeadLine: LocalDate
)
