package jp.glory.detekt.practice.todo.usecase

import jp.glory.detekt.practice.base.usecase.UseCase
import jp.glory.detekt.practice.todo.domain.event.TodoEventPublisher
import jp.glory.detekt.practice.todo.domain.repository.TodoRepository

@UseCase
class StartTodoUseCase(
    private val todoRepository: TodoRepository,
    private val todoEventPublisher: TodoEventPublisher
) {
    fun start(input: Input) =
        todoRepository.findById(input.id)
            ?.let { it.start() }
            ?.also { todoEventPublisher.publishStarted(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
    )
}