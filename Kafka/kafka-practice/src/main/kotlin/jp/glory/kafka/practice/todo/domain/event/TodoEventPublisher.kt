package jp.glory.kafka.practice.todo.domain.event

interface TodoEventPublisher {
    fun publishChanged(changedTodo: ChangedTodo)
    fun publishDeleted(deletedTodo: DeletedTodo)
    fun publishFinished(finishedTodo: FinishedTodo)
    fun publishRegistered(registeredTodo: RegisteredTodo)
    fun publishStarted(startedTodo: StartedTodo)
}