package jp.glory.kafka.practice.lib.kafka.todo.topic

import java.time.LocalDate

class ChangedTodoTopicValue(
    val id: String,
    val newTitle: String,
    val oldTile: String,
    val newDeadLine: LocalDate,
    val oldDeadLine: LocalDate
)