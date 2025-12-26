package jp.glory.practice.boot.app.user.command.domain.event

interface UserEventHandler {
    fun handleUserCreated(event: UserCreated)
}
