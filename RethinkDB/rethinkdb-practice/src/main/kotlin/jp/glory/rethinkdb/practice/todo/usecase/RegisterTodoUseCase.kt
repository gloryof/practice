package jp.glory.rethinkdb.practice.todo.usecase

import jp.glory.rethinkdb.practice.base.usecase.UseCase
import jp.glory.rethinkdb.practice.todo.domain.event.RegisteredTodo
import jp.glory.rethinkdb.practice.todo.domain.model.TodoIdGenerator
import jp.glory.rethinkdb.practice.todo.domain.repository.TodoRepository
import java.time.LocalDate

@UseCase
class RegisterTodoUseCase(
    private val todoIdGenerator: TodoIdGenerator,
    private val todoRepository: TodoRepository
) {
    fun register(input: Input): Output =
        createEvent(input)
            .also { todoRepository.registerRegisteredEvent(it) }
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