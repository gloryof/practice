package jp.glory.rethinkdb.practice.todo.infra.repository

import jp.glory.rethinkdb.practice.infra.store.dao.*
import jp.glory.rethinkdb.practice.todo.domain.event.*
import jp.glory.rethinkdb.practice.todo.domain.model.Todo
import jp.glory.rethinkdb.practice.todo.domain.model.ProgressStatus
import jp.glory.rethinkdb.practice.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TodoRepositoryImpl : TodoRepository {
    override fun findById(id: String): Todo? =
        TodoDao.findById(id)
            ?.let { toDomainModel(it) }

    override fun findAll(): List<Todo> =
        TodoDao.findAll()
            .map { toDomainModel(it) }

    override fun registerRegisteredEvent(registeredTodo: RegisteredTodo) {
        val records = toRecords(registeredTodo)
        TodoDao.register(records.todo)
        NotificationDao.register(records.notification)
    }

    override fun registerChangedEvent(changedTodo: ChangedTodo) {
        val before = TodoDao.findById(changedTodo.id) ?: throw IllegalStateException("Not found")
        val after = before.copy(
            title = changedTodo.newTitle,
            deadLine = changedTodo.newDeadLine
        )
        val notification = toNotificationRecord(
            id = after.id,
            after = after,
            before = before,
            modifiedTypeValue = ModifiedTypeValue.Changed
        )
        TodoDao.update(after)
        NotificationDao.register(notification)
    }

    override fun registerDeletedEvent(deletedTodo: DeletedTodo) {
        val todo = TodoDao.findById(deletedTodo.id) ?: throw IllegalStateException("Not found")
        val notification = toNotificationRecord(
            id = todo.id,
            after = null,
            before = todo,
            modifiedTypeValue = ModifiedTypeValue.Deleted
        )
        TodoDao.delete(todo.id)
        NotificationDao.register(notification)
    }

    override fun registerStatedEvent(startedTodo: StartedTodo) {
        val before = TodoDao.findById(startedTodo.id) ?: throw IllegalStateException("Not found")
        val progressFlags = createTodoFlags(ProgressStatus.Started)
        val after = before.copy(
            started = progressFlags.started,
            finished = progressFlags.finished
        )
        val notification = toNotificationRecord(
            id = after.id,
            after = after,
            before = before,
            modifiedTypeValue = ModifiedTypeValue.Started
        )
        TodoDao.update(after)
        NotificationDao.register(notification)
    }

    override fun registerFinishedEvent(finishedTodo: FinishedTodo) {
        val before = TodoDao.findById(finishedTodo.id) ?: throw IllegalStateException("Not found")
        val progressFlags = createTodoFlags(ProgressStatus.Finished)
        val after = before.copy(
            started = progressFlags.started,
            finished = progressFlags.finished
        )
        val notification = toNotificationRecord(
            id = after.id,
            after = after,
            before = before,
            modifiedTypeValue = ModifiedTypeValue.Finished
        )
        TodoDao.update(after)
        NotificationDao.register(notification)
    }

    private fun toDomainModel(todoRecord: TodoRecord): Todo =
        Todo(
            id = todoRecord.id,
            title = todoRecord.title,
            deadLine = todoRecord.deadLine,
            started = todoRecord.started,
            finished = todoRecord.finished
        )

    private fun toRecords(
        registeredTodo: RegisteredTodo
    ): Records {
        val todo = toTodoRecord(registeredTodo)
        return Records(
            todo = todo,
            notification = toNotificationRecord(
                id = todo.id,
                after = todo,
                before = null,
                modifiedTypeValue = ModifiedTypeValue.Registered
            )
        )
    }

    private fun toTodoRecord(
        registeredTodo: RegisteredTodo
    ): TodoRecord =
        TodoRecord(
            id = registeredTodo.id,
            title = registeredTodo.title,
            deadLine = registeredTodo.deadLine,
            started = false,
            finished = false
        )

    private fun toNotificationRecord(
        id: String,
        after: TodoRecord?,
        before: TodoRecord?,
        modifiedTypeValue: ModifiedTypeValue,
    ): NotificationRecord =
        NotificationRecord(
            id  = UUID.randomUUID().toString(),
            targetTodoId = id,
            modifiedTypeCode = modifiedTypeValue.code,
            before = before?.let { toNotificationContentRecord(it) },
            after = after?.let { toNotificationContentRecord(it) }
        )

    private fun toNotificationContentRecord(
        todo: TodoRecord
    ): NotificationContentRecord =
        NotificationContentRecord(
            title = todo.title,
            deadLine = todo.deadLine,
            started = todo.started,
            finished = todo.finished
        )

    private fun createTodoFlags(progressStatus: ProgressStatus) =
        when(progressStatus) {
            ProgressStatus.UnStarted -> TodoFlags(
                started = false,
                finished = false
            )
            ProgressStatus.Started -> TodoFlags(
                started = true,
                finished = false
            )
            ProgressStatus.Finished -> TodoFlags(
                started = true,
                finished = true
            )
        }

    private class TodoFlags(
        val started: Boolean,
        val finished: Boolean
    )

    private class Records(
        val todo: TodoRecord,
        val notification: NotificationRecord
    )
}