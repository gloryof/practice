package jp.glory.ktor.practice.base.use_case

sealed class UseCaseError

class UseCaseUnknownError(
    val cause: Throwable
) : UseCaseError()

class UseCaseAuthenticationError(
    val type: Type
) : UseCaseError() {
    enum class Type {
        User,
        Token
    }
}

class UseCaseNotFoundError(
    val resourceName: ResourceName,
    val idValue: String,
) : UseCaseError() {
    enum class ResourceName {
        User
    }
}
