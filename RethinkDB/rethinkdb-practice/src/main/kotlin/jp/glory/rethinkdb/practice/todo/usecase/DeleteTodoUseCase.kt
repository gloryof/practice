package jp.glory.rethinkdb.practice.todo.usecase

import jp.glory.rethinkdb.practice.base.usecase.UseCase
import jp.glory.rethinkdb.practice.todo.domain.repository.TodoRepository

@UseCase
class DeleteTodoUseCase(
    private val todoRepository: TodoRepository
) {
    fun delete(input: Input) =
        todoRepository.findById(input.id)
            ?.let { it.delete() }
            ?.also { todoRepository.registerDeletedEvent(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
    )
}