package jp.glory.detekt.practice.todo.usecase

import jp.glory.detekt.practice.todo.domain.model.ProgressStatus
import jp.glory.detekt.practice.todo.domain.model.Todo
import java.time.Clock
import java.time.LocalDate

class TodoDetail(
    val id: String,
    val title: String,
    val deadLine: LocalDate,
    val progressStatus: TodoProgressStatusValue,
    val isOverDue: Boolean
) {
    constructor(
        todo: Todo,
        clock: Clock
    ) : this(
        id = todo.id,
        title = todo.title,
        deadLine = todo.deadLine,
        progressStatus = TodoProgressStatusValue.create(
            todo.getProgressStatus()
        ),
        isOverDue = todo.isOverDue(clock)
    )
}

enum class TodoProgressStatusValue {
    UnStarted,
    Started,
    Finished;

    companion object {
        fun create(progressStatus: ProgressStatus) =
            when(progressStatus) {
                ProgressStatus.UnStarted -> UnStarted
                ProgressStatus.Started -> Started
                ProgressStatus.Finished -> Finished
            }
    }
}