package jp.glory.oauth.practice.authorization.api.token.request

import com.fasterxml.jackson.annotation.JsonProperty
import jp.glory.oauth.practice.authorization.api.token.RegisterTokenBase
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.spec.GrantedClient

data class CodeRequest(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("redirect_uri") val redirectUri: String,
    @JsonProperty("client_id") val clientId: String
) {
    fun validate(): Either<RegisterTokenBase.Errors, Unit> {
        if (GrantedClient.codeFrom(clientId) == null) {
            return Left(
                RegisterTokenBase.Errors(
                    type = RegisterTokenBase.ErrorType.UnauthorizedClient,
                    message = "This client is not authorized."
                )
            )
        }

        if (grantType != "authorization_code") {
            return Left(
                RegisterTokenBase.Errors(
                    type = RegisterTokenBase.ErrorType.InvalidRequest,
                    message = "Grant type is invalid."
                )
            )
        }

        return Right(Unit)
    }
}