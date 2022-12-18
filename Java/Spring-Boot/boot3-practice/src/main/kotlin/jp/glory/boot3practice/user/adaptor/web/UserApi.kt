package jp.glory.boot3practice.user.adaptor.web

import com.github.michaelbull.result.mapBoth
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebApi
import jp.glory.boot3practice.base.adaptor.web.WebExceptionHelper
import jp.glory.boot3practice.user.use_case.FindUserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
            .map { toUsersResponse(it) }
            .map { UsersResponse(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ): Mono<UserResponse> =
        Mono.just(
            findUserUseCase.findById(
                FindUserUseCase.FindInput(id)
            )
        )
            .flatMap { result ->
                result.mapBoth(
                    success = { Mono.just(it) },
                    failure = { Mono.error(WebExceptionHelper.create(it))}
                )
            }
            .map { toUserResponse(it) }

    private fun toUsersResponse(output: FindUserUseCase.UsersOutput): List<UserResponse> =
        output.users
            .map { toUserResponse(it) }

    private fun toUserResponse(output: FindUserUseCase.UserOutput): UserResponse =
        UserResponse(
            name = output.name,
            birthDay = output.birthDay
        )
}