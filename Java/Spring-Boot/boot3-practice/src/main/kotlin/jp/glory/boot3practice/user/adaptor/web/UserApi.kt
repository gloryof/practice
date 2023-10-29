package jp.glory.boot3practice.user.adaptor.web

import com.github.michaelbull.result.mapBoth
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebApi
import jp.glory.boot3practice.base.adaptor.web.WebExceptionHelper
import jp.glory.boot3practice.user.use_case.FindUserUseCase
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import java.io.ByteArrayInputStream
import java.io.InputStream
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

    @GetMapping("/csv")
    fun getUsersByCsv(): Mono<ResponseEntity<InputStreamResource>> =
        Mono.just(findUserUseCase.findAllUsers())
            .map { toCsvLines(it) }
            .map { toInputStream(it) }
            .map { ResponseEntity.ok().body(InputStreamResource(it)) }

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

    private fun toCsvLines(output: FindUserUseCase.UsersOutput): List<String> {
        val firstLine = "no,name,birth-day"
        val lines = mutableListOf(firstLine)

        output.users
            .mapIndexed { index, user -> "$index,${user.name},${user.birthDay}" }
            .let { lines.addAll(it) }

        return lines
    }

    private fun toInputStream(lines:List<String>): InputStream =
        lines.joinToString("\n")
            .let { ByteArrayInputStream(it.toByteArray()) }
}