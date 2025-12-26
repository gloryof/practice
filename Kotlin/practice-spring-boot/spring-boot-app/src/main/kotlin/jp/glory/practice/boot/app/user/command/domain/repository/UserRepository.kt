package jp.glory.practice.boot.app.user.command.domain.repository

import jp.glory.practice.boot.app.user.command.domain.model.LoginId

interface UserRepository {
    fun existLoginId(loginId: LoginId): Boolean
}
