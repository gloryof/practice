package jp.glory.practice.graphql.document.base.adaptor.web

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.slf4j.LoggerFactory
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Component

@Component
class WebExceptionHandler : DataFetcherExceptionResolverAdapter() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun resolveToMultipleErrors(
        ex: Throwable,
        env: DataFetchingEnvironment
    ): List<GraphQLError> {
        logger.error("Error", ex)
        return when (ex) {
            is WebException -> handleWebException(ex, env)
            else -> super.resolveToMultipleErrors(ex, env)?.toList() ?: emptyList()
        }
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
    ): List<GraphQLError> {
        logger.error(error.message, error.cause)

        return GraphqlErrorBuilder.newError(env)
            .errorType(ErrorType.INTERNAL_ERROR)
            .message("Unknown error is occurred.")
            .build()
            .let { listOf(it) }
    }

    private fun handleNotFoundError(
        error: WebNotFoundError,
        env: DataFetchingEnvironment
    ): List<GraphQLError> =
        GraphqlErrorBuilder.newError(env)
            .errorType(ErrorType.NOT_FOUND)
            .message("${error.resourceName} is not found.")
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
        error.details.map {
            GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.BAD_REQUEST)
                .message(it.createMessage())
                .extensions(
                    it.toAttribute()
                )
                .build()
        }
}