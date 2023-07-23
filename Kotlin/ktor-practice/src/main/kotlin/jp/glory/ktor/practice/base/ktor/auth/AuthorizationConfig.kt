package jp.glory.ktor.practice.base.ktor.auth

import com.github.michaelbull.result.mapBoth
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import jp.glory.ktor.practice.auth.use_case.AuthenticateUseCase
import jp.glory.ktor.practice.base.adaptor.web.WebNotAuthorizedException

fun Application.configureAuthorization(
    auth: AuthenticateUseCase
) {
    install(Authentication) {
        bearer {
            authenticate { credential ->
                auth.authenticateToken(AuthenticateUseCase.TokenAuthInput(credential.token))
                    .mapBoth(
                        success = { UserIdPrincipal(it.id) },
                        failure = { throw WebNotAuthorizedException }
                    )
            }

        }
    }
}
