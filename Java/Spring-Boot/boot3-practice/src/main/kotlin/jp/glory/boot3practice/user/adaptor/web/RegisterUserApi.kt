package jp.glory.boot3practice.user.adaptor.web

import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebApi
import jp.glory.boot3practice.user.use_case.RegisterUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import java.time.LocalDate

@WebApi
@RequestMapping(EndpointConst.User.register)
class RegisterUserApi(
    private val registerUserUseCase: RegisterUserUseCase,
) {

    class UserRegisterRequest(
        val name: String? = null,
        val birthDay: LocalDate? = null,
        val password: String? = null
    )
    data class UserRegisterResponse(
        val id: String
    )

    @PostMapping
    fun registerUser(
        @RequestBody request: UserRegisterRequest
    ): Mono<ResponseEntity<UserRegisterResponse>> =
        Mono.just(toUserRegisterInput(request))
            .map { registerUserUseCase.register(it) }
            .map { toUserRegisterResponse(it) }
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    private fun toUserRegisterInput(request: UserRegisterRequest): RegisterUserUseCase.Input =
        RegisterUserUseCase.Input(
            name = request.name ?: "",
            birthDay = request.birthDay ?: LocalDate.now(),
            password = request.password ?: ""
        )

    private fun toUserRegisterResponse(output: RegisterUserUseCase.Output): UserRegisterResponse =
        UserRegisterResponse(output.id)
}