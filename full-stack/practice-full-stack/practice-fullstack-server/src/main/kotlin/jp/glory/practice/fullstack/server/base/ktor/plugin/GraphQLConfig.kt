package jp.glory.practice.fullstack.server.base.ktor.plugin

import graphql.GraphQL
import graphql.kickstart.tools.GraphQLResolver
import graphql.kickstart.tools.SchemaParserBuilder
import graphql.scalars.ExtendedScalars
import io.ktor.server.application.Application
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.UserQuery
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
        .scalars(ExtendedScalars.Date)
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
    val userQuery by inject<UserQuery>()
    return listOf(
        userQuery
    )
}