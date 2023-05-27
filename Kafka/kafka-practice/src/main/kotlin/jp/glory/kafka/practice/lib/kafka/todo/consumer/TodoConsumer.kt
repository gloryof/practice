package jp.glory.kafka.practice.lib.kafka.todo.consumer

import jp.glory.kafka.practice.lib.kafka.todo.topic.RegisteredTodoTopicValue
import jp.glory.kafka.practice.lib.kafka.todo.topic.TopicNameConst
import jp.glory.kafka.practice.lib.store.dao.TodoDao
import jp.glory.kafka.practice.lib.store.dao.TodoRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TodoConsumer(
    private val todoDao: TodoDao
) {
    @KafkaListener(id = "test", topics = [TopicNameConst.RegisteredTodo])
    fun listenRegistered(value: String) {
        println(value)
        /*todoDao.register(
            TodoRecord(
                id = value.id,
                title = value.title,
                deadLine = value.deadLine,
                started = false,
                finished = false
            )
        )*/
    }
    @KafkaListener(topics = [TopicNameConst.ChangedTodo])
    fun changedRegistered(value: String) {
        println(value)
        /*todoDao.register(
            TodoRecord(
                id = value.id,
                title = value.title,
                deadLine = value.deadLine,
                started = false,
                finished = false
            )
        )*/
    }
}