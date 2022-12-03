package jp.glory.boot3practice.user.adaptor.web

import jp.glory.boot3practice.base.adaptor.web.WebApi
import jp.glory.boot3practice.user.use_case.FindUserUseCase
import jp.glory.boot3practice.user.use_case.RegisterUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import java.time.LocalDate

@WebApi
@RequestMapping("/api/users")
class UserApi(
    private val registerUserUseCase: RegisterUserUseCase,
    private val findUserUseCase: FindUserUseCase
) {
    data class UsersResponse(
        val users: List<UserResponse>
    )
    data class UserResponse(
        val name: String,
        val birthDay: LocalDate
    )
    data class UserRegisterRequest(
        val name: String?,
        val birthDay: LocalDate?,
        val password: String?
    )
    inner class UserRegisterResponse(
        val id: String
    )

    @GetMapping
    fun getUsers(): Mono<UsersResponse> =
        Mono.just(findUserUseCase.findAllUsers())
            .map { toUserResponse(it) }
            .map { UsersResponse(it) }

    @PostMapping
    fun create(
        @RequestBody request: UserRegisterRequest
    ): Mono<ResponseEntity<UserRegisterResponse>> =
        Mono.just(toUserRegisterInput(request))
            .map { registerUserUseCase.register(it) }
            .map { toUserRegisterResponse(it) }
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    private fun toUserResponse(output: FindUserUseCase.UsersOutput): List<UserResponse> =
        output.users
            .map {
                UserResponse(
                    name = it.name,
                    birthDay = it.birthDay
                )
            }

    private fun toUserRegisterInput(request: UserRegisterRequest): RegisterUserUseCase.Input =
        RegisterUserUseCase.Input(
            name = request.name ?: "",
            birthDay = request.birthDay ?: LocalDate.now(),
            password = request.password ?: ""
        )

    private fun toUserRegisterResponse(output: RegisterUserUseCase.Output): UserRegisterResponse =
        UserRegisterResponse(output.id)
}