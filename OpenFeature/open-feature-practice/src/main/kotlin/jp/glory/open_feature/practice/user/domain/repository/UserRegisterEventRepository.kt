package jp.glory.open_feature.practice.user.domain.repository

import jp.glory.open_feature.practice.user.domain.event.UserRegisterEvent

interface UserRegisterEventRepository {
    fun save(
        event: UserRegisterEvent
    )
}