package jp.glory.oauth.practice.resource.context.user.domain.repository

import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.context.user.domain.model.User
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId

interface UserRepository {
    fun findById(id: UserId): Either<DomainUnknownError, User?>
}