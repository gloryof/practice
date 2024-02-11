package jp.glory.practice.fullstack.server.base.ktor.plugin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import graphql.GraphQL
import graphql.kickstart.tools.GraphQLResolver
import graphql.kickstart.tools.ObjectMapperConfigurer
import graphql.kickstart.tools.ObjectMapperConfigurerContext
import graphql.kickstart.tools.SchemaParserBuilder
import graphql.kickstart.tools.SchemaParserOptions
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLScalarType
import io.ktor.server.application.Application
import jp.glory.practice.fullstack.server.review.adaptor.graphql.ReviewResolvers
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.UserResolvers
import org.koin.ktor.ext.inject
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import kotlin.io.path.name

fun Application.createGraphQL(): GraphQL {
    val builder = SchemaParserBuilder()

    loadSchema(javaClass.classLoader, builder)

    val schema = builder
        .resolvers(resolvers())
        .scalars(scalars())
        .options(options())
        .build()
        .makeExecutableSchema()

    return GraphQL.newGraphQL(schema).build()
}

private fun loadSchema(
    classLoader: ClassLoader,
    builder: SchemaParserBuilder
) {
    val directoryName = "graphql"

    val basePath = classLoader.getResource(directoryName)
        ?.let { Paths.get(it.toURI()) }
        ?: return
    val targets = Files.list(basePath)

    targets
        .filter { it.fileName.name.endsWith("gql") }
        .map { it.absolutePathString().replace(basePath.toString(), "") }
        .forEach { builder.file("$directoryName$it") }
}

private fun Application.resolvers(): List<GraphQLResolver<*>> {
    val userResolvers by inject<UserResolvers>()
    val reviewResolvers by inject<ReviewResolvers>()

    return userResolvers.getResolvers() +
            reviewResolvers.getResolvers()
}

private fun scalars(): List<GraphQLScalarType> =
    listOf(
        ExtendedScalars.Date,
        ExtendedScalars.DateTime
    )


private fun options(): SchemaParserOptions =
    SchemaParserOptions.newOptions()
        .objectMapperConfigurer(CustomeObjectMapperConfigurer())
        .build()


private class CustomeObjectMapperConfigurer : ObjectMapperConfigurer {
    override fun configure(
        mapper: ObjectMapper,
        context: ObjectMapperConfigurerContext
    ) {
        mapper.registerModule(JavaTimeModule())
    }

}
