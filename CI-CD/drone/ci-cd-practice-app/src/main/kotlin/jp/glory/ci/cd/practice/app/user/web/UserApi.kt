package jp.glory.ci.cd.practice.app.user.web

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import jp.glory.ci.cd.practice.app.base.web.WebExceptionHelper
import jp.glory.ci.cd.practice.app.user.usecase.DeleteUserById
import jp.glory.ci.cd.practice.app.user.usecase.FindUserById
import jp.glory.ci.cd.practice.app.user.usecase.RegisterUser
import jp.glory.ci.cd.practice.app.user.usecase.UpdateUser
import jp.glory.ci.cd.practice.app.user.web.request.RegisterUserRequest
import jp.glory.ci.cd.practice.app.user.web.request.UpdateUserRequest
import jp.glory.ci.cd.practice.app.user.web.response.RegisterUserResponse
import jp.glory.ci.cd.practice.app.user.web.response.UpdateUserResponse
import jp.glory.ci.cd.practice.app.user.web.response.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class UserApi(
    private val findUserById: FindUserById,
    private val registerUser: RegisterUser,
    private val updateUser: UpdateUser,
    private val deleteUserById: DeleteUserById
) {
    @GetMapping("/{userId}")
    fun findById(
        @PathVariable userId: String
    ): ResponseEntity<UserResponse> =
        findUserById.find(
            FindUserById.Input(
                userId = userId
            )
        )
            .flatMap { Ok(mapToUserResponse(it)) }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @PostMapping
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<RegisterUserResponse> =
        convertToRegisterInput(request)
            .let { registerUser.register(it) }
            .flatMap { Ok(mapToUserRegisterResponse(it)) }
            .mapBoth(
                success = { ResponseEntity.status(HttpStatus.CREATED).body(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @PutMapping("/{userId}")
    fun update(
        @PathVariable userId: String,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UpdateUserResponse> =
        convertToUpdateInput(userId, request)
            .let { updateUser.update(it) }
            .flatMap { Ok(mapToUserUpdateResponse(it)) }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @DeleteMapping("/{userId}")
    fun deleteById(
        @PathVariable userId: String
    ): ResponseEntity<Unit> =
        deleteUserById.delete(
            DeleteUserById.Input(
                userId = userId
            )
        )
            .mapBoth(
                success = { ResponseEntity.noContent().build() },
                failure = { throw WebExceptionHelper.create(it) }
            )

    private fun mapToUserResponse(
        output: FindUserById.Output
    ): UserResponse =
        UserResponse(
            userId = output.userId,
            givenName = output.givenName,
            familyName = output.familyName
        )

    private fun mapToUserRegisterResponse(
        output: RegisterUser.Output
    ): RegisterUserResponse =
        RegisterUserResponse(
            userId = output.userId,
        )

    private fun mapToUserUpdateResponse(
        output: UpdateUser.Output
    ): UpdateUserResponse =
        UpdateUserResponse(
            userId = output.userId,
        )

    private fun convertToRegisterInput(
        request: RegisterUserRequest
    ): RegisterUser.Input =
        RegisterUser.Input(
            givenName = request.givenName,
            familyName = request.familyName,
            password = request.password
        )

    private fun convertToUpdateInput(
        userId: String,
        request: UpdateUserRequest
    ): UpdateUser.Input =
        UpdateUser.Input(
            userId = userId,
            givenName = request.givenName,
            familyName = request.familyName,
        )
}