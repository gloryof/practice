package jp.glory.ci.cd.practice.app.user.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError
import jp.glory.ci.cd.practice.app.user.domain.model.RegisterUserEvent
import jp.glory.ci.cd.practice.app.user.domain.model.UpdateUserEvent
import jp.glory.ci.cd.practice.app.user.domain.model.User
import jp.glory.ci.cd.practice.app.user.domain.model.UserId

interface UserRepository {
    fun findById(id: UserId): Result<User?, DomainUnknownError>
    fun register(event: RegisterUserEvent): Result<UserId, DomainUnknownError>
    fun update(event: UpdateUserEvent): Result<UserId, DomainUnknownError>
    fun delete(id: UserId): Result<Unit, DomainUnknownError>
}