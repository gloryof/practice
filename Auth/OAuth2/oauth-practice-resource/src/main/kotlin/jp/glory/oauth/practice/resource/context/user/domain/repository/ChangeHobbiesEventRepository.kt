package jp.glory.oauth.practice.resource.context.user.domain.repository

import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.context.user.domain.event.ChangeHobbiesEvent

interface ChangeHobbiesEventRepository {
    fun save(event: ChangeHobbiesEvent): Either<DomainUnknownError, Unit>
}