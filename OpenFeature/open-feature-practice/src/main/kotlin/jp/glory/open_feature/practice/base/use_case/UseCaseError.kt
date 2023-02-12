package jp.glory.open_feature.practice.base.use_case

sealed class UseCaseError

class UseCaseUnknownError(
    val cause: Throwable
) : UseCaseError()

class UseCaseNotFoundError(
    val resourceName: ResourceName,
    val idValue: String,
) : UseCaseError() {
    enum class ResourceName {
        User,
        Product,
        Member,
        Service
    }
}