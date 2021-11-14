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
            is NotFoundError -> handleNotFoundError(ex.error, env)
            is WebUnknownError -> handleWebUnknownError(ex.error, env)
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
        error: NotFoundError,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        GraphqlErrorBuilder.newError(env)
            .errorType(ErrorType.NOT_FOUND)
            .message(error.message)
            .build()
            .let { listOf(it) }
}
