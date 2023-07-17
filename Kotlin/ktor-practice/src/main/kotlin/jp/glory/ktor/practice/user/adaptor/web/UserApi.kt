package jp.glory.ktor.practice.user.adaptor.web

import com.github.michaelbull.result.mapBoth
import jp.glory.ktor.practice.base.adaptor.web.WebExceptionHelper
import jp.glory.ktor.practice.user.use_case.FindUserUseCase
import java.time.LocalDate

class UserApi(
    private val findUserUseCase: FindUserUseCase
) {
    data class UsersResponse(
        val users: List<UserResponse>
    )
    data class UserResponse(
        val name: String,
        val birthDay: LocalDate
    )
    fun getUsers(): UsersResponse =
        findUserUseCase.findAllUsers()
            .let { toUsersResponse(it) }
            .let { UsersResponse(it) }

    fun getById(
        id: String
    ): UserResponse =
        findUserUseCase.findById(
            FindUserUseCase.FindInput(id)
        )
            .mapBoth(
                success = { toUserResponse(it) },
                failure = { throw WebExceptionHelper.create(it)}
            )

    private fun toUsersResponse(output: FindUserUseCase.UsersOutput): List<UserResponse> =
        output.users
            .map { toUserResponse(it) }

    private fun toUserResponse(output: FindUserUseCase.UserOutput): UserResponse =
        UserResponse(
            name = output.name,
            birthDay = output.birthDay
        )
}
