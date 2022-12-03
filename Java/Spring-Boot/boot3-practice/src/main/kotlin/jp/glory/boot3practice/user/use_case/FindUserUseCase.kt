package jp.glory.boot3practice.user.use_case

import jp.glory.boot3practice.base.use_case.UseCase
import jp.glory.boot3practice.user.domain.repository.UserRepository
import java.time.LocalDate

@UseCase
class FindUserUseCase(
    private val userRepository: UserRepository
) {
    class UsersOutput(
        val users: List<UserOutput>
    )
    class UserOutput(
        val name: String,
        val birthDay: LocalDate
    )

    fun findAllUsers(): UsersOutput =
        userRepository.findAllUsers()
            .map {
                UserOutput(
                    name = it.name.value,
                    birthDay = it.birthDay.value
                )
            }
            .let { UsersOutput(it) }
}