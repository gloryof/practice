package jp.glory.rethinkdb.practice.notify.usecase

import jp.glory.rethinkdb.practice.notify.domain.model.ModifiedContent
import jp.glory.rethinkdb.practice.notify.domain.model.ModifiedType
import jp.glory.rethinkdb.practice.notify.domain.model.Notification
import jp.glory.rethinkdb.practice.todo.usecase.TodoProgressStatusValue
import java.time.LocalDate

class NotificationDetail(
    val targetTodoId: String,
    val modifiedType: ModifiedTypeValue,
    val before: ModifiedContentDetail?,
    val after: ModifiedContentDetail?
) {
    constructor(notification: Notification) : this(
        targetTodoId = notification.targetTodoId,
        modifiedType = ModifiedTypeValue.create(notification.modifiedType),
        before = notification.before?.let { ModifiedContentDetail(it) },
        after = notification.after?.let { ModifiedContentDetail(it) }
    )
}

class ModifiedContentDetail(
    val title: String,
    val deadLine: LocalDate,
    val status: TodoProgressStatusValue,
) {
    constructor(modifiedContent: ModifiedContent) : this(
        title = modifiedContent.title,
        deadLine = modifiedContent.deadLine,
        status = TodoProgressStatusValue.create(modifiedContent.status)
    )
}

enum class ModifiedTypeValue {
    Registered,
    Changed,
    Started,
    Finished,
    Deleted;

    companion object {
        fun create(modifiedType: ModifiedType): ModifiedTypeValue =
            when(modifiedType) {
                ModifiedType.Registered -> Registered
                ModifiedType.Changed -> Changed
                ModifiedType.Started -> Started
                ModifiedType.Finished -> Finished
                ModifiedType.Deleted -> Deleted
            }
    }
}