package jp.glory.oauth.practice.resource.context.user.usecase

import jp.glory.oauth.practice.resource.base.*
import jp.glory.oauth.practice.resource.base.domain.UserError
import jp.glory.oauth.practice.resource.base.usecase.UseCase
import jp.glory.oauth.practice.resource.base.usecase.UseCaseError
import jp.glory.oauth.practice.resource.base.usecase.mapToUseCaseError
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId
import jp.glory.oauth.practice.resource.context.user.domain.repository.UserRepository

@UseCase
class FindUser(
    private val repository: UserRepository
) {
    fun findUserInfo(
        loginId: String
    ): Either<UseCaseError, UserInfo> =
        repository.findById(UserId(loginId))
            .flatMap {
                if (it == null) {
                    Left(UserError(UserError.Type.UserNotFound))
                } else {
                    Right(it)
                }
            }
            .map {
                UserInfo(
                    name = it.name.value,
                    hobbies = it.hobbies.map { hobby -> hobby.name }
                )
            }
            .mapBoth(
                right = { Right(it) },
                left = { Left(mapToUseCaseError(it)) }
            )

    data class UserInfo(
        val name: String,
        val hobbies: List<String>
    )
}