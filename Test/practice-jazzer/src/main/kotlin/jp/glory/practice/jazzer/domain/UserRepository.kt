package jp.glory.practice.jazzer.domain

import com.github.michaelbull.result.Result

interface UserRepository {
    fun save(user: User): Result<Unit, DomainError>
    fun generateId(): Result<UserId, DomainError>
}