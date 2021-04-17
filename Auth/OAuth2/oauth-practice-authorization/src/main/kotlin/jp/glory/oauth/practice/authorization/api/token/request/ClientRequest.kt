package jp.glory.oauth.practice.authorization.api.token.request

import com.fasterxml.jackson.annotation.JsonProperty
import jp.glory.oauth.practice.authorization.api.token.RegisterTokenBase
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.spec.Scope

data class ClientRequest(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("scope") val scope: String,
) {
    fun validate(): Either<RegisterTokenBase.Errors, Unit> {
        if (grantType != "client_credentials") {
            return Left(
                RegisterTokenBase.Errors(
                    type = RegisterTokenBase.ErrorType.InvalidRequest,
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