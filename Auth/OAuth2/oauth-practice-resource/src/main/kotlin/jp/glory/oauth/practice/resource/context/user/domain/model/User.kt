package jp.glory.oauth.practice.resource.context.user.domain.model

import jp.glory.oauth.practice.resource.base.system.UUIDGenerator

data class User(
    val id: UserId,
    val name: UserName,
    val hobbies: List<Hobby>
)

data class UserId(val value: String)
data class UserName(val value: String)
data class Hobby(val name: String)

class UserIdGenerator(private val generator: UUIDGenerator) {
    fun generate() = UserId(generator.generate())
}