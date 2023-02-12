package jp.glory.open_feature.practice.user.adaptor.web

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import jp.glory.open_feature.practice.base.adaptor.web.EndpointConst
import jp.glory.open_feature.practice.base.adaptor.web.WebApi
import jp.glory.open_feature.practice.base.adaptor.web.WebExceptionHelper
import jp.glory.open_feature.practice.user.use_case.FindUserUseCase
import jp.glory.open_feature.practice.user.use_case.RegisterUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@WebApi
@RequestMapping(EndpointConst.User.user)
class UserApi(
    private val findUserUseCase: FindUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) {
    data class UsersResponse(
        val users: List<UserResponse>
    )
    data class UserResponse(
        val name: String,
        val birthDay: LocalDate
    )
    @GetMapping
    fun getUsers(): UsersResponse =
        findUserUseCase.findAllUsers()
            .let { toUsersResponse(it) }
            .let { UsersResponse(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ): UserResponse =
        findUserUseCase.findById(
            FindUserUseCase.FindInput(id)
        )
        .map { toUserResponse(it) }
        .mapBoth(
                success = { it },
                failure = { throw WebExceptionHelper.create(it) }
        )

    @PostMapping
    fun registerUser(
        @RequestBody request: UserRegisterRequest
    ): ResponseEntity<UserRegisterResponse> =
        toUserRegisterInput(request)
            .let { registerUserUseCase.register(it) }
            .let { toUserRegisterResponse(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    private fun toUserRegisterInput(request: UserRegisterRequest): RegisterUserUseCase.Input =
        RegisterUserUseCase.Input(
            name = request.name ?: "",
            birthDay = request.birthDay ?: LocalDate.now(),
            password = request.password ?: ""
        )

    private fun toUserRegisterResponse(output: RegisterUserUseCase.Output): UserRegisterResponse =
        UserRegisterResponse(output.id)

    private fun toUsersResponse(output: FindUserUseCase.UsersOutput): List<UserResponse> =
        output.users
            .map { toUserResponse(it) }

    private fun toUserResponse(output: FindUserUseCase.UserOutput): UserResponse =
        UserResponse(
            name = output.name,
            birthDay = output.birthDay
        )
    class UserRegisterRequest(
        val name: String? = null,
        val birthDay: LocalDate? = null,
        val password: String? = null
    )
    data class UserRegisterResponse(
        val id: String
    )
}