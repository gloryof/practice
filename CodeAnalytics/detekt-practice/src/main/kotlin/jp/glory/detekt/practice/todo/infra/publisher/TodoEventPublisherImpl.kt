package jp.glory.detekt.practice.todo.infra.publisher

import jp.glory.detekt.practice.lib.store.dao.TodoDao
import jp.glory.detekt.practice.lib.store.dao.TodoRecord
import jp.glory.detekt.practice.todo.domain.event.ChangedTodo
import jp.glory.detekt.practice.todo.domain.event.DeletedTodo
import jp.glory.detekt.practice.todo.domain.event.FinishedTodo
import jp.glory.detekt.practice.todo.domain.event.RegisteredTodo
import jp.glory.detekt.practice.todo.domain.event.StartedTodo
import jp.glory.detekt.practice.todo.domain.event.TodoEventPublisher
import org.springframework.stereotype.Component

@Component
class TodoEventPublisherImpl(
    private val todoDao: TodoDao
) : TodoEventPublisher {
    override fun publishChanged(changedTodo: ChangedTodo) {
        todoDao
            .findById(changedTodo.id)
            ?.let {
                it.copy(
                    title = changedTodo.title,
                    deadLine = changedTodo.deadLine
                )
            }
            ?.let { todoDao.update(it) }
    }

    override fun publishDeleted(deletedTodo: DeletedTodo) {
        todoDao.delete(deletedTodo.id)
    }

    override fun publishFinished(finishedTodo: FinishedTodo) {
        todoDao
            .findById(finishedTodo.id)
            ?.let {
                it.copy(finished = true)
            }
            ?.let { todoDao.update(it) }
    }

    override fun publishRegistered(registeredTodo: RegisteredTodo) {
        TodoRecord(
            id = registeredTodo.id,
            title = registeredTodo.title,
            deadLine = registeredTodo.deadLine,
            started = false,
            finished = false
        )
            .let { todoDao.register(it) }
    }

    override fun publishStarted(startedTodo: StartedTodo) {
        todoDao
            .findById(startedTodo.id)
            ?.let {
                it.copy(started = true)
            }
            ?.let { todoDao.update(it) }
    }

}
