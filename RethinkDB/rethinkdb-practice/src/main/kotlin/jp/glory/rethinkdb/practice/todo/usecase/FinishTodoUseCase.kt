package jp.glory.rethinkdb.practice.todo.usecase

import jp.glory.rethinkdb.practice.base.usecase.UseCase
import jp.glory.rethinkdb.practice.todo.domain.repository.TodoRepository

@UseCase
class FinishTodoUseCase(
    private val todoRepository: TodoRepository
) {
    fun finish(input: Input) =
        todoRepository.findById(input.id)
            ?.let { it.finish() }
            ?.also { todoRepository.registerFinishedEvent(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
    )
}