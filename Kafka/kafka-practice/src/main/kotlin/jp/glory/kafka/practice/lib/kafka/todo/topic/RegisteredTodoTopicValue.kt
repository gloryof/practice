package jp.glory.kafka.practice.lib.kafka.todo.topic

import java.time.LocalDate

class RegisteredTodoTopicValue(
    val id: String,
    val title: String,
    val deadLine: LocalDate
)