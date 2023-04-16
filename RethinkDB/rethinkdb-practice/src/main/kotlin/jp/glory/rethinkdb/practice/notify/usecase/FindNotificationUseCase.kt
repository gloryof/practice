package jp.glory.rethinkdb.practice.notify.usecase

import jp.glory.rethinkdb.practice.base.usecase.UseCase
import jp.glory.rethinkdb.practice.notify.domain.repository.NotificationRepository

@UseCase
class FindNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    fun findAll(): Output =
        notificationRepository.findAll()
            .let { notifications -> notifications.map { NotificationDetail(it) } }
            .let { Output(it) }

    class Output(
        val details: List<NotificationDetail>
    )
}