package jp.glory.detekt.practice.todo.domain.repository

import jp.glory.detekt.practice.todo.domain.model.Todo

interface TodoRepository {
    fun findById(id: String): Todo?
    fun findAll(): List<Todo>
}
