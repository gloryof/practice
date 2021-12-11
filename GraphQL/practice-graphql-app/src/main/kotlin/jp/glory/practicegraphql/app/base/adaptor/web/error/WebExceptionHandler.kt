package jp.glory.practicegraphql.app.base.adaptor.web.error

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Component

@Component
class WebExceptionHandler : DataFetcherExceptionResolverAdapter() {
    override fun resolveToMultipleErrors(
        ex: Throwable,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        when (ex) {
            is WebException -> handleWebException(ex, env)
            else -> super.resolveToMultipleErrors(ex, env)?.toList() ?: emptyList()
        }

    private fun handleWebException(
        ex: WebException,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        when (ex.error) {
            is WebUnknownError -> handleWebUnknownError(ex.error, env)
            is WebNotFoundError -> handleNotFoundError(ex.error, env)
            is WebValidationError -> handleValidationError(ex.error, env)
        }

    private fun handleWebUnknownError(
        error: WebUnknownError,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        GraphqlErrorBuilder.newError(env)
            .errorType(ErrorType.INTERNAL_ERROR)
            .message(error.message)
            .build()
            .let { listOf(it) }

    private fun handleNotFoundError(
        error: WebNotFoundError,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        GraphqlErrorBuilder.newError(env)
            .errorType(ErrorType.NOT_FOUND)
            .message(error.message)
            .extensions(
                mapOf(
                    "id" to error.idValue,
                    "resource" to error.resourceName
                )
            )
            .build()
            .let { listOf(it) }

    private fun handleValidationError(
        error: WebValidationError,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        GraphqlErrorBuilder.newError(env)
            .errorType(ErrorType.BAD_REQUEST)
            .message(error.message)
            .extensions(
                mapOf("details" to error.details)
            )
            .build()
            .let { listOf(it) }
}
