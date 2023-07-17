package jp.glory.ktor.practice.user.adaptor.web

import jp.glory.ktor.practice.user.use_case.RegisterUserUseCase
import java.time.LocalDate

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

    fun registerUser(
        request: UserRegisterRequest
    ): UserRegisterResponse =
        toUserRegisterInput(request)
            .let { registerUserUseCase.register(it) }
            .let { toUserRegisterResponse(it) }

    private fun toUserRegisterInput(request: UserRegisterRequest): RegisterUserUseCase.Input =
        RegisterUserUseCase.Input(
            name = request.name ?: "",
            birthDay = request.birthDay ?: LocalDate.now(),
            password = request.password ?: ""
        )

    private fun toUserRegisterResponse(output: RegisterUserUseCase.Output): UserRegisterResponse =
        UserRegisterResponse(output.id)
}
