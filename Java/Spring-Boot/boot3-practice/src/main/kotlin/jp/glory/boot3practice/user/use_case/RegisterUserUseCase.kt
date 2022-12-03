package jp.glory.boot3practice.user.use_case

import jp.glory.boot3practice.base.use_case.UseCase
import jp.glory.boot3practice.user.domain.event.InputPassword
import jp.glory.boot3practice.user.domain.event.UserRegisterEvent
import jp.glory.boot3practice.user.domain.model.BirthDay
import jp.glory.boot3practice.user.domain.model.UserId
import jp.glory.boot3practice.user.domain.model.UserName
import jp.glory.boot3practice.user.domain.repository.UserRegisterEventRepository
import java.time.LocalDate

@UseCase
class RegisterUserUseCase(
    private val userRegisterEventRepository: UserRegisterEventRepository
) {
    class Input(
        val name: String,
        val birthDay: LocalDate,
        val password: String
    )
    inner class Output(
        val id: String
    )
    fun register(input: Input): Output {
        val id = UserId.generate()
        return UserRegisterEvent(
            id = id,
            name = UserName(input.name),
            birthDay = BirthDay(input.birthDay),
            password = InputPassword(input.password)
        )
            .let { userRegisterEventRepository.save(it) }
            .let { Output(id.value) }
    }
}