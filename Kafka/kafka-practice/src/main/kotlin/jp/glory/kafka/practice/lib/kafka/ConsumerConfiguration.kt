package jp.glory.kafka.practice.lib.kafka

import jp.glory.kafka.practice.lib.kafka.todo.topic.TopicNameConst
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin


@Configuration
class ConsumerConfiguration {
    fun topics(
        kafkaProperties: KafkaProperties
    ) =
        listOf(
            registeredTodoTopic(),
            changedTodoTopic(),
            statedTodoTopic(),
            finishedTodoTopic(),
            deletedTodoTopic()
        )
            .let { KafkaAdmin.NewTopics(*it.toTypedArray()) }

    private fun registeredTodoTopic(): NewTopic = TopicBuilder.name(TopicNameConst.RegisteredTodo).build()
    private fun changedTodoTopic(): NewTopic = TopicBuilder.name(TopicNameConst.ChangedTodo).build()
    private fun statedTodoTopic(): NewTopic = TopicBuilder.name(TopicNameConst.StartedTodo).build()
    private fun finishedTodoTopic(): NewTopic = TopicBuilder.name(TopicNameConst.FinishedTodo).build()
    private fun deletedTodoTopic(): NewTopic = TopicBuilder.name(TopicNameConst.DeletedTodo).build()

}