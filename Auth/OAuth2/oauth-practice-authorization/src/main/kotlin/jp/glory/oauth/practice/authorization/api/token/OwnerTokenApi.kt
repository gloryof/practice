package jp.glory.oauth.practice.authorization.api.token

import jp.glory.oauth.practice.authorization.api.token.request.OwnerRequest
import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.spec.owner.OwnerCredential
import jp.glory.oauth.practice.authorization.store.AccessTokenStore
import jp.glory.oauth.practice.authorization.store.RefreshTokenStore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/token/owner")
class OwnerTokenApi(
    accessTokenStore: AccessTokenStore,
    refreshTokenStore: RefreshTokenStore
) : RegisterTokenBase(accessTokenStore, refreshTokenStore) {
    @PostMapping("/owner")
    fun generate(
        @RequestBody request: OwnerRequest,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<out Any> =
        request
            .validate()
            .flatMap { validateCredential(authorization) }
            .flatMap { credential ->
                registerToken(request.toScope())
                    .map { Pair(credential, it) }
            }
            .flatMap { (credential, token) ->
                registerRefreshToken(token)
                    .map { Pair(credential, it) }
            }
            .mapBoth(
                right = { (credential, result) ->
                    createSuccessResponse(
                        result = result,
                        userId = credential.id
                    )
                },
                left = { createErrorResponse(it) }
            )

    private fun validateCredential(
        value: String
    ): Either<Errors, EncodedCredential> {
        val decodedValue = decodeCredential(value)
        val isAuthorized =
            OwnerCredential.isMatch(
                ownerId = decodedValue.id,
                password = decodedValue.password
            )

        if (!isAuthorized) {
            return Left(
                Errors(
                    message = "Authorization is fail",
                    type = ErrorType.InvalidRequest
                )
            )
        }

        return Right(decodedValue)
    }

    private fun decodeCredential(value: String): EncodedCredential {
        val splitValue = value.split("Basic ")
        if (splitValue.size < 2) {
            return EncodedCredential("", "")
        }

        val decodedValue = Base64.getDecoder().decode(splitValue[1])
        val idPassword = String(decodedValue).split(":")

        if (idPassword.size < 2) {
            return EncodedCredential("", "")
        }

        return EncodedCredential(
            id = idPassword[0],
            password = idPassword[1],
        )
    }

    data class EncodedCredential(
        val id: String,
        val password: String
    )
}