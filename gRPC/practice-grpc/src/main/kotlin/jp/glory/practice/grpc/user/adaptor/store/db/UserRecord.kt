package jp.glory.practice.grpc.user.adaptor.store.db

import jp.glory.practice.grpc.user.domain.model.Birthday
import jp.glory.practice.grpc.user.domain.model.User
import jp.glory.practice.grpc.user.domain.model.UserId
import jp.glory.practice.grpc.user.domain.model.UserName
import java.time.LocalDate

class UserRecord(
    val id: String,
    val userName: String,
    val birthday: LocalDate
) {
    fun toDomain(): User =
        User(
            id = UserId(id),
            name = UserName(userName),
            birthday = Birthday(birthday)
        )
}