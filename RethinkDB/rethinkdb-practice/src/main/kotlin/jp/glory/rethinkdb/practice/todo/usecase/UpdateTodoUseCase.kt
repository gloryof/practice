package jp.glory.rethinkdb.practice.todo.usecase

import jp.glory.rethinkdb.practice.base.usecase.UseCase
import jp.glory.rethinkdb.practice.todo.domain.repository.TodoRepository
import java.time.Clock
import java.time.LocalDate

@UseCase
class UpdateTodoUseCase(
    private val todoRepository: TodoRepository,
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
            ?.let { todoRepository.registerChangedEvent(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
        val newTitle: String,
        val newDeadLine: LocalDate
    )
}