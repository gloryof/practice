package jp.glory.kafka.practice.todo.infra.reposiotry

import jp.glory.kafka.practice.lib.store.dao.TodoDao
import jp.glory.kafka.practice.lib.store.dao.TodoRecord
import jp.glory.kafka.practice.todo.domain.model.Todo
import jp.glory.kafka.practice.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {
    override fun findById(id: String): Todo? =
        todoDao.findById(id)
            ?.let { toDomainModel(it) }

    override fun findAll(): List<Todo> =
        todoDao.findAll()
            .map { toDomainModel(it) }

    private fun toDomainModel(todoRecord: TodoRecord): Todo =
        Todo(
            id = todoRecord.id,
            title = todoRecord.title,
            deadLine = todoRecord.deadLine,
            started = todoRecord.started,
            finished = todoRecord.finished
        )
}