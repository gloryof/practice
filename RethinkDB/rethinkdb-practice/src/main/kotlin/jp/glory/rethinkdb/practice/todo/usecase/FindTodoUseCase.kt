package jp.glory.rethinkdb.practice.todo.usecase

import jp.glory.rethinkdb.practice.base.usecase.UseCase
import jp.glory.rethinkdb.practice.todo.domain.repository.TodoRepository
import java.time.Clock

@UseCase
class FindTodoUseCase(
    private val todoRepository: TodoRepository,
    private val clock: Clock
) {
    fun findById(input: Input): Output =
        todoRepository.findById(input.id)
            ?.let { TodoDetail(it, clock) }
            ?.let { Output(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String
    )

    class Output(
        val detail: TodoDetail
    )
}