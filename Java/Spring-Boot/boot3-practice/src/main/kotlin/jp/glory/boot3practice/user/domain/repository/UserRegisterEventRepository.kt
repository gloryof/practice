package jp.glory.boot3practice.user.domain.repository

import jp.glory.boot3practice.user.domain.event.UserRegisterEvent

interface UserRegisterEventRepository {
    fun save(
        event: UserRegisterEvent
    )
}