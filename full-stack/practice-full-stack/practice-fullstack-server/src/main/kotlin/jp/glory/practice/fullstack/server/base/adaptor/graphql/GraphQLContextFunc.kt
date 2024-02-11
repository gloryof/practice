package jp.glory.practice.fullstack.server.base.adaptor.graphql

import graphql.schema.DataFetchingEnvironment
import jp.glory.practice.fullstack.server.base.ktor.plugin.GraphQLContextKeys

fun DataFetchingEnvironment.getGraphQLExecutor(): GraphQLExecutor =
    graphQlContext.get<GraphQLExecutor>(GraphQLContextKeys.Authorization.name)