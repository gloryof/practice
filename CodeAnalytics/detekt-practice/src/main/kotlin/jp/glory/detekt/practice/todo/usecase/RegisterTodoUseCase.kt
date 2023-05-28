package jp.glory.detekt.practice.todo.usecase

import jp.glory.detekt.practice.base.usecase.UseCase
import jp.glory.detekt.practice.todo.domain.event.RegisteredTodo
import jp.glory.detekt.practice.todo.domain.event.TodoEventPublisher
import jp.glory.detekt.practice.todo.domain.model.TodoIdGenerator
import jp.glory.detekt.practice.todo.domain.repository.TodoRepository
import java.time.LocalDate

@UseCase
class RegisterTodoUseCase(
    private val todoIdGenerator: TodoIdGenerator,
    private val todoEventPublisher: TodoEventPublisher
) {
    fun register(input: Input): Output =
        createEvent(input)
            .also { todoEventPublisher.publishRegistered(it) }
            .let { Output(it.id) }

    private fun createEvent(
        input: Input
    ): RegisteredTodo = RegisteredTodo(
        id = todoIdGenerator.generate(),
        title = input.title,
        deadLine = input.deadLine
    )

    class Input(
        val title: String,
        val deadLine: LocalDate,
    )

    class Output(
        val id: String
    )
}
