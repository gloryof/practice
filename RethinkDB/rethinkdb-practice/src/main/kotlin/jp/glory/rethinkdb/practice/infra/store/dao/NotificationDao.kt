package jp.glory.rethinkdb.practice.infra.store.dao

import java.time.LocalDate

object NotificationDao {
    private val notifies = mutableMapOf<String, NotificationRecord>()

    fun findById(id: String): NotificationRecord? = notifies[id]
    fun findAll(): List<NotificationRecord> = notifies.values.toList()

    fun register(notificationRecord: NotificationRecord) {
        notifies[notificationRecord.id] = notificationRecord
    }
}

data class NotificationRecord(
    val id: String,
    val targetTodoId: String,
    val modifiedTypeCode: Int,
    val before: NotificationContentRecord?,
    val after: NotificationContentRecord?

)
enum class ModifiedTypeValue(val code: Int){
    Registered(1),
    Changed(2),
    Started(3),
    Finished(4),
    Deleted(5);

    companion object {
        fun fromCode(value: Int): ModifiedTypeValue =
            ModifiedTypeValue
                .values()
                .first { it.code == value }
    }
}

data class NotificationContentRecord(
    val title: String,
    val deadLine: LocalDate,
    val started: Boolean,
    val finished: Boolean
)