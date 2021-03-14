package jp.glory.oauth.practice.client.lib

import jp.glory.oauth.practice.client.base.Either
import jp.glory.oauth.practice.client.base.Right
import jp.glory.oauth.practice.client.config.ResourceServerConfig
import jp.glory.oauth.practice.client.config.ServerConfig
import org.springframework.stereotype.Component

interface ResourceSeverClient {
    fun findById(
        accessToken: String,
        id: String
    ): Either<Unit, UserResponse>
    fun generateLoginUrl(
        redirectUrl: String
    ): String

    data class UserResponse(
        val id: String,
        val name: String,
        val hobbies: List<String>
    )
}

@Component
class ResourceSeverClientImpl(
    private val resourceConfig: ResourceServerConfig,
    private val serverConfig: ServerConfig
) : ResourceSeverClient {
    override fun findById(accessToken: String, id: String): Either<Unit, ResourceSeverClient.UserResponse> =
        UserJsonResponse(
            id = "test-id",
            name = "test-name",
            hobbies = listOf("Game", "Billiard")
        )
            .let {
                ResourceSeverClient.UserResponse(
                    id = it.id,
                    name = it.name,
                    hobbies = it.hobbies
                )
            }
            .let { Right(it) }

    override fun generateLoginUrl(redirectUrl: String): String =
        resourceConfig.url

    data class UserJsonResponse(
        val id: String,
        val name: String,
        val hobbies: List<String>
    )
}