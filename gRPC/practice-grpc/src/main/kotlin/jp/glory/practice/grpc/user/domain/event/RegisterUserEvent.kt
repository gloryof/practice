package jp.glory.practice.grpc.user.domain.event

import jp.glory.practice.grpc.user.domain.model.Birthday
import jp.glory.practice.grpc.user.domain.model.UserId
import jp.glory.practice.grpc.user.domain.model.UserIdGenerator
import jp.glory.practice.grpc.user.domain.model.UserName
import java.time.LocalDate

class RegisterUserEvent private constructor(
    val userId: UserId,
    val userName: UserName,
    val birthday: Birthday
) {
    companion object {
        fun create(
            userName: String,
            birthday: LocalDate
        ): RegisterUserEvent =
            RegisterUserEvent(
                userId = UserIdGenerator.generate(),
                userName =  UserName(userName),
                birthday = Birthday(birthday)
            )
    }
}

interface RegisterUserEventHandler {
    fun register(event: RegisterUserEvent)
}