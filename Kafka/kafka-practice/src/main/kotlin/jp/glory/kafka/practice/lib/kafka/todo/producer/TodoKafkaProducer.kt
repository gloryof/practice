package jp.glory.kafka.practice.lib.kafka.todo.producer

import jp.glory.kafka.practice.lib.kafka.todo.topic.TopicNameConst
import jp.glory.kafka.practice.lib.kafka.todo.topic.*
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TodoKafkaProducer(
    private val registeredTotoTemplate: KafkaTemplate<String, String>,
    private val changedTotoTemplate: KafkaTemplate<String, ChangedTodoTopicValue>,
    private val startedTotoTemplate: KafkaTemplate<String, StartedTodoTopicValue>,
    private val finishedTotoTemplate: KafkaTemplate<String, FinishedTodoTopicValue>,
    private val deletedTotoTemplate: KafkaTemplate<String, DeletedTodoTopicValue>,
) {
    fun sendRegistered(topicValue: RegisteredTodoTopicValue) =
        registeredTotoTemplate.send(
            TopicNameConst.RegisteredTodo,
            topicValue.id
        )

    fun sendChanged(topicValue: ChangedTodoTopicValue) =
        changedTotoTemplate.send(
            TopicNameConst.ChangedTodo,
            topicValue
        )

    fun sendStarted(topicValue: StartedTodoTopicValue) =
        startedTotoTemplate.send(
            TopicNameConst.StartedTodo,
            topicValue
        )

    fun sendFinished(topicValue: FinishedTodoTopicValue) =
        finishedTotoTemplate.send(
            TopicNameConst.FinishedTodo,
            topicValue
        )

    fun sendDeleted(topicValue: DeletedTodoTopicValue) =
        deletedTotoTemplate.send(
            TopicNameConst.DeletedTodo,
            topicValue
        )
}


