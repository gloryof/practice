package jp.glory.oauth.practice.resource.context.user.domain.repository

import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.context.user.domain.event.UserRegisterEvent

interface UserRegisterEventRepository {
    fun save(event: UserRegisterEvent): Either<DomainUnknownError, Unit>
}