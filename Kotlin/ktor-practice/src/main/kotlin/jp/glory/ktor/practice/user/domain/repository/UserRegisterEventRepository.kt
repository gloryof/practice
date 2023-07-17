package jp.glory.ktor.practice.user.domain.repository

import jp.glory.ktor.practice.user.domain.event.UserRegisterEvent

interface UserRegisterEventRepository {
    fun save(
        event: UserRegisterEvent
    )
}