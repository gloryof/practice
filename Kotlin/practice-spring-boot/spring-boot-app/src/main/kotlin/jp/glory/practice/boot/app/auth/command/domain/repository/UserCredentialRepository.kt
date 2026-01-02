package jp.glory.practice.boot.app.auth.command.domain.repository

import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.UserCredential

interface UserCredentialRepository {
    fun findByLoginId(loginId: LoginId): UserCredential?
}
