package jp.glory.rethinkdb.practice.notify.domain.model

import jp.glory.rethinkdb.practice.todo.domain.model.ProgressStatus
import java.time.LocalDate

class Notification(
    val targetTodoId: String,
    val modifiedType: ModifiedType,
    val before: ModifiedContent?,
    val after: ModifiedContent?
)

class ModifiedContent(
    val title: String,
    val deadLine: LocalDate,
    val status: ProgressStatus,
)

enum class ModifiedType {
    Registered,
    Changed,
    Started,
    Finished,
    Deleted
}
