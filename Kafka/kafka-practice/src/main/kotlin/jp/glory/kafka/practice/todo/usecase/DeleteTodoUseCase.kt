package jp.glory.kafka.practice.todo.usecase

import jp.glory.kafka.practice.base.usecase.UseCase
import jp.glory.kafka.practice.todo.domain.event.TodoEventPublisher
import jp.glory.kafka.practice.todo.domain.repository.TodoRepository

@UseCase
class DeleteTodoUseCase(
    private val todoRepository: TodoRepository,
    private val todoEventPublisher: TodoEventPublisher
) {
    fun delete(input: Input) =
        todoRepository.findById(input.id)
            ?.let { it.delete() }
            ?.also { todoEventPublisher.publishDeleted(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
    )
}