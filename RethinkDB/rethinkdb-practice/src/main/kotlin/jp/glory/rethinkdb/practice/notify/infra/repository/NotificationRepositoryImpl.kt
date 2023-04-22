package jp.glory.rethinkdb.practice.notify.infra.repository

import jp.glory.rethinkdb.practice.infra.store.dao.ModifiedTypeValue
import jp.glory.rethinkdb.practice.infra.store.dao.NotificationContentRecord
import jp.glory.rethinkdb.practice.infra.store.dao.NotificationDao
import jp.glory.rethinkdb.practice.infra.store.dao.NotificationRecord
import jp.glory.rethinkdb.practice.notify.domain.model.ModifiedContent
import jp.glory.rethinkdb.practice.notify.domain.model.ModifiedType
import jp.glory.rethinkdb.practice.notify.domain.model.Notification
import jp.glory.rethinkdb.practice.notify.domain.repository.NotificationRepository
import jp.glory.rethinkdb.practice.todo.domain.model.ProgressStatus
import org.springframework.stereotype.Repository

@Repository
class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    override fun findAll(): List<Notification> =
        notificationDao.findAll()
            .map { toDomainModel(it) }

    private fun toDomainModel(
        record: NotificationRecord
    ): Notification =
        Notification(
            targetTodoId = record.targetTodoId,
            modifiedType = when(ModifiedTypeValue.fromCode(record.modifiedTypeCode)) {
                ModifiedTypeValue.Registered -> ModifiedType.Registered
                ModifiedTypeValue.Changed -> ModifiedType.Changed
                ModifiedTypeValue.Started -> ModifiedType.Started
                ModifiedTypeValue.Finished -> ModifiedType.Finished
                ModifiedTypeValue.Deleted -> ModifiedType.Deleted
            },
            after = record.after?.let { toModifiedContent(it) },
            before = record.before?.let { toModifiedContent(it) }
        )

    private fun toModifiedContent(
        record: NotificationContentRecord
    ): ModifiedContent =
        ModifiedContent(
            title = record.title,
            deadLine = record.deadLine,
            status = toProgressStatus(
                started = record.started,
                finished = record.finished
            )
        )

    private fun toProgressStatus(
        started: Boolean,
        finished: Boolean
    ): ProgressStatus {
        if (finished) {
            return ProgressStatus.Finished
        }
        if (started) {
            return ProgressStatus.Started
        }
        return ProgressStatus.UnStarted
    }
}