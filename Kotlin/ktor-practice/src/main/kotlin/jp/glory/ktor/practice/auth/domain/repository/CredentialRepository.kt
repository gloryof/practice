package jp.glory.ktor.practice.auth.domain.repository

import jp.glory.ktor.practice.auth.domain.model.UserId
import jp.glory.ktor.practice.auth.domain.model.Credential

interface CredentialRepository {
    fun findById(userId: UserId): Credential?
}
