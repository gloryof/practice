package jp.glory.kafka.practice.todo.domain.repository

import jp.glory.kafka.practice.todo.domain.model.Todo

interface TodoRepository {
    fun findById(id: String): Todo?
    fun findAll(): List<Todo>
}