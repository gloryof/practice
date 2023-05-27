package jp.glory.kafka.practice.todo.usecase

import jp.glory.kafka.practice.base.usecase.UseCase
import jp.glory.kafka.practice.todo.domain.event.TodoEventPublisher
import jp.glory.kafka.practice.todo.domain.repository.TodoRepository
import java.time.Clock
import java.time.LocalDate

@UseCase
class UpdateTodoUseCase(
    private val todoRepository: TodoRepository,
    private val todoEventPublisher: TodoEventPublisher,
    private val clock: Clock
) {
    fun update(input: Input) =
        todoRepository.findById(input.id)
            ?.let {
                it.change(
                    newTitle = input.newTitle,
                    newDeadLine = input.newDeadLine,
                    clock = clock
                )
            }
            ?.let { todoEventPublisher.publishChanged(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
        val newTitle: String,
        val newDeadLine: LocalDate
    )
}