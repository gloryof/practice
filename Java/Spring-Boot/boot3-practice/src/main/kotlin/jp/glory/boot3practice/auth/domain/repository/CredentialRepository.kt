package jp.glory.boot3practice.auth.domain.repository

import jp.glory.boot3practice.auth.domain.model.UserId
import jp.glory.boot3practice.auth.domain.model.Credential

interface CredentialRepository {
    fun findById(userId: UserId): Credential?
}