package jp.glory.practicegraphql.app.libs.graqphql

import graphql.analysis.MaxQueryComplexityInstrumentation
import graphql.analysis.MaxQueryDepthInstrumentation
import graphql.execution.instrumentation.Instrumentation
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.boot.GraphQlSourceBuilderCustomizer
import org.springframework.graphql.execution.GraphQlSource

@Configuration
class GraphQLCustomizer(
    private val graphQLConfig: GraphQLConfig
) : GraphQlSourceBuilderCustomizer {
    override fun customize(builder: GraphQlSource.Builder) {
        builder.instrumentation(createInstrumentationList())
    }

    private fun createInstrumentationList(): List<Instrumentation> =
        listOf(
            MaxQueryDepthInstrumentation(graphQLConfig.maxDepth),
            MaxQueryComplexityInstrumentation(graphQLConfig.maxComplex)
        )
}
