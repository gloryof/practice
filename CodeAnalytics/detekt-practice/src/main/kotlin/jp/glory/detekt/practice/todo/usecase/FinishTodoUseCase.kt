package jp.glory.detekt.practice.todo.usecase

import jp.glory.detekt.practice.base.usecase.UseCase
import jp.glory.detekt.practice.todo.domain.event.TodoEventPublisher
import jp.glory.detekt.practice.todo.domain.repository.TodoRepository

@UseCase
class FinishTodoUseCase(
    private val todoRepository: TodoRepository,
    private val todoEventPublisher: TodoEventPublisher
) {
    fun finish(input: Input) =
        todoRepository.findById(input.id)
            ?.let { it.finish() }
            ?.also { todoEventPublisher.publishFinished(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
    )
}
