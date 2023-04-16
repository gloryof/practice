package jp.glory.rethinkdb.practice.notify.domain.repository

import jp.glory.rethinkdb.practice.notify.domain.model.Notification

interface NotificationRepository {
    fun findAll(): List<Notification>
}