package jp.glory.practice.fullstack.server.base.ktor.plugin

import graphql.ExecutionInput
import graphql.GraphQL
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import jp.glory.practice.fullstack.server.auth.authRoute
import jp.glory.practice.fullstack.server.auth.usecase.GetAuthorizedUser
import jp.glory.practice.fullstack.server.base.adaptor.graphql.GraphQLExecutor
import jp.glory.practice.fullstack.server.base.adaptor.graphql.GraphQLRequest
import jp.glory.practice.fullstack.server.base.exception.AuthorizationException
import jp.glory.practice.fullstack.server.user.userRoute
import org.koin.ktor.ext.inject


fun Application.configureRouting(
    graphQL: GraphQL,
) {
    val getAuthorizedUser by inject<GetAuthorizedUser>()
    routing {
        route("/api") {
            authRoute()
            userRoute()
        }
        graphQLRoute(graphQL, getAuthorizedUser)
    }
}

private fun Route.graphQLRoute(
    graphQL: GraphQL,
    getAuthorizedUser: GetAuthorizedUser
) {
    post("/graphql") {
        val executor = call.getToken()
            ?.let { getAuthorizedUser.getUserByToken(GetAuthorizedUser.Input(it)) }
            ?.let { GraphQLExecutor(it.userId) }
            ?: throw AuthorizationException("Authorization is failed")

        val request = call.receive<GraphQLRequest>()
        val result = graphQL
            .execute(
                ExecutionInput.newExecutionInput()
                    .graphQLContext(
                        mapOf(
                            GraphQLContextKeys.Authorization.name to executor
                        )
                    )
                    .query(request.query)
                    .operationName(request.operationName)
                    .variables(request.variables)
            )

        call.respond(result.toSpecification())
    }
}

private fun ApplicationCall.getToken(): String? =
    request.headers["Authorization"]
        ?.let { it.split(" ") }
        ?.let {
            if (it.size < 2 || it.first() != "Bearer") {
                null
            } else {
                it[1]
            }
        }