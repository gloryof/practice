package jp.glory.practicegraphql.app.libs.graqphql

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "graphql")
@ConstructorBinding
data class GraphQLConfig(
    val maxDepth: Int,
    val maxComplex: Int
)
