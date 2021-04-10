package jp.glory.oauth.practice.authorization.api.token.request

import com.fasterxml.jackson.annotation.JsonProperty
import jp.glory.oauth.practice.authorization.api.token.TokenApi
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.spec.Scope

data class OwnerRequest(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("scope") val scope: String,
) {
    fun validate(): Either<TokenApi.Errors, Unit> {
        if (grantType != "password") {
            return Left(
                TokenApi.Errors(
                    type = TokenApi.ErrorType.InvalidRequest,
                    message = "Grant type is invalid."
                )
            )
        }

        return Right(Unit)
    }

    fun toScope(): List<Scope> =
        scope.split(" ")
            .mapNotNull { Scope.codeFrom(it) }

}