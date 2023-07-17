package jp.glory.ktor.practice.user.use_case

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ktor.practice.base.use_case.UseCaseNotFoundError
import jp.glory.ktor.practice.user.domain.model.User
import jp.glory.ktor.practice.user.domain.model.UserId
import jp.glory.ktor.practice.user.domain.repository.UserRepository
import java.time.LocalDate

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

    class FindInput(
        val id: String
    )

    fun findAllUsers(): UsersOutput =
        userRepository.findAllUsers()
            .map { toUserOutput(it) }
            .let { UsersOutput(it) }

    fun findById(input: FindInput): Result<UserOutput, UseCaseNotFoundError> =
        userRepository.findById(UserId(input.id))
            ?.let { Ok(toUserOutput(it)) }
            ?: Err(
                UseCaseNotFoundError(
                    resourceName = UseCaseNotFoundError.ResourceName.User,
                    idValue = input.id
                )
            )

    private fun toUserOutput(user: User): UserOutput =
        UserOutput(
            name = user.name.value,
            birthDay = user.birthDay.value
        )

}
