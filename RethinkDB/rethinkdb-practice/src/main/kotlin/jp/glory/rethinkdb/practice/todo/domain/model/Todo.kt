package jp.glory.rethinkdb.practice.todo.domain.model

import jp.glory.rethinkdb.practice.todo.domain.event.ChangedTodo
import jp.glory.rethinkdb.practice.todo.domain.event.DeletedTodo
import jp.glory.rethinkdb.practice.todo.domain.event.FinishedTodo
import jp.glory.rethinkdb.practice.todo.domain.event.StartedTodo
import jp.glory.rethinkdb.practice.todo.domain.service.DeadLineService
import java.time.Clock
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

class TodoIdGenerator {
    fun generate(): String = UUID.randomUUID().toString()
}

data class Todo(
    val id: String,
    val title: String,
    val deadLine: LocalDate,
    private val started: Boolean,
    private val finished: Boolean
) {
    fun getProgressStatus(): ProgressStatus =
        when {
            finished -> ProgressStatus.Finished
            started -> ProgressStatus.Started
            else -> ProgressStatus.UnStarted
        }

    fun getDaysLeft(clock: Clock): Long {
        return ChronoUnit.DAYS.between(LocalDate.now(clock), deadLine)
    }

    fun isOverDue(clock: Clock): Boolean = DeadLineService.isOverDue(
        date = deadLine,
        clock = clock
    )

    fun change(
        newTitle: String,
        newDeadLine: LocalDate,
        clock: Clock
    ): ChangedTodo {
        if (DeadLineService.isOverDue(newDeadLine, clock)) {
            throw IllegalArgumentException("Dead line must future.")
        }
        return ChangedTodo(
            id = this.id,
            newTitle = newTitle,
            oldTile = title,
            newDeadLine = newDeadLine,
            oldDeadLine = deadLine
        )
    }

    fun start(): StartedTodo = StartedTodo(id)

    fun finish(): FinishedTodo = FinishedTodo(id)

    fun delete(): DeletedTodo = DeletedTodo(id)
}

enum class ProgressStatus {
    UnStarted,
    Started,
    Finished
}
