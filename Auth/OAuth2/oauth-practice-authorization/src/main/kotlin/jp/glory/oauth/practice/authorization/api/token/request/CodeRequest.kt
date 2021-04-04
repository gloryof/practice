package jp.glory.oauth.practice.authorization.api.token.request

import com.fasterxml.jackson.annotation.JsonProperty
import jp.glory.oauth.practice.authorization.api.token.TokenApi
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.spec.GrantedClient

data class CodeRequest(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("redirect_uri") val redirectUri: String,
    @JsonProperty("client_id") val clientId: String
) {
    fun validate(): Either<TokenApi.Errors, Unit> {
        if (GrantedClient.codeFrom(clientId) == null) {
            return Left(
                TokenApi.Errors(
                    type = TokenApi.ErrorType.UnauthorizedClient,
                    message = "This client is not authorized."
                )
            )
        }

        if (grantType != "authorization_code") {
            return Left(
                TokenApi.Errors(
                    type = TokenApi.ErrorType.InvalidRequest,
                    message = "Grant type is invalid."
                )
            )
        }

        return Right(Unit)
    }
}