package jp.glory.kafka.practice.todo.infra.publisher

import jp.glory.kafka.practice.lib.kafka.todo.producer.*
import jp.glory.kafka.practice.lib.kafka.todo.topic.*
import jp.glory.kafka.practice.todo.domain.event.*
import org.springframework.stereotype.Component

@Component
class TodoEventPublisherImpl(
    private val todoKafkaProducer: TodoKafkaProducer
) : TodoEventPublisher {
    override fun publishChanged(changedTodo: ChangedTodo) {
        ChangedTodoTopicValue(
            id = changedTodo.id,
            newTitle = changedTodo.newTitle,
            oldTile = changedTodo.oldTile,
            newDeadLine = changedTodo.newDeadLine,
            oldDeadLine = changedTodo.oldDeadLine
        )
            .let { todoKafkaProducer.sendChanged(it) }
    }

    override fun publishDeleted(deletedTodo: DeletedTodo) {
        DeletedTodoTopicValue(
            id = deletedTodo.id
        )
            .let { todoKafkaProducer.sendDeleted(it) }
    }

    override fun publishFinished(finishedTodo: FinishedTodo) {
        FinishedTodoTopicValue(
            id = finishedTodo.id
        )
            .let { todoKafkaProducer.sendFinished(it) }
    }

    override fun publishRegistered(registeredTodo: RegisteredTodo) {
        RegisteredTodoTopicValue(
            id = registeredTodo.id,
            title = registeredTodo.title,
            deadLine = registeredTodo.deadLine
        )
            .let { todoKafkaProducer.sendRegistered(it) }
    }

    override fun publishStarted(startedTodo: StartedTodo) {
        StartedTodoTopicValue(
            id = startedTodo.id
        )
            .let { todoKafkaProducer.sendStarted(it) }
    }

}