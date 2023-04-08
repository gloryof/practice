package jp.glory.rethinkdb.practice.todo.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Todo(
    val id: String,
    private var title: String,
    private var deadLine: LocalDate,
    private var started: Boolean,
    private var finished: Boolean
) {
    fun getTitle(): String = title
    fun getDeadLine(): LocalDate = deadLine
    fun getStatus(): TodoStatus =
        when {
            finished -> TodoStatus.Finished
            isOverdue(deadLine) -> TodoStatus.Overdue
            started -> TodoStatus.Started
            else -> TodoStatus.UnStarted
        }

    fun getDaysLeft(): Long {
        return ChronoUnit.DAYS.between(LocalDate.now(), deadLine)
    }

    fun change(
        newTitle: String,
        newDeadLine: LocalDate
    ) {
        if (isOverdue(newDeadLine)) {
            throw IllegalArgumentException("Dead line must future.")
        }
        title = newTitle
        deadLine = newDeadLine
    }

    fun start() {
        started = true
    }

    fun finish() {
        finished = true
    }

    private fun isOverdue(
        date: LocalDate
    ): Boolean = date.isBefore(LocalDate.now())
}

enum class TodoStatus {
    UnStarted,
    Overdue,
    Started,
    Finished
}