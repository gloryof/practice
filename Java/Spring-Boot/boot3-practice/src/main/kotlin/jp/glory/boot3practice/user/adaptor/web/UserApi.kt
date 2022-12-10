package jp.glory.boot3practice.user.adaptor.web

import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebApi
import jp.glory.boot3practice.user.use_case.FindUserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import java.time.LocalDate

@WebApi
@RequestMapping(EndpointConst.User.user)
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
    @GetMapping
    fun getUsers(): Mono<UsersResponse> =
        Mono.just(findUserUseCase.findAllUsers())
            .map { toUserResponse(it) }
            .map { UsersResponse(it) }


    private fun toUserResponse(output: FindUserUseCase.UsersOutput): List<UserResponse> =
        output.users
            .map {
                UserResponse(
                    name = it.name,
                    birthDay = it.birthDay
                )
            }
}