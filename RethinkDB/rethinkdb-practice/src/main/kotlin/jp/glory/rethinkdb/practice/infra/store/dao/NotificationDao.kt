package jp.glory.rethinkdb.practice.infra.store.dao

import com.rethinkdb.gen.ast.Table
import jp.glory.rethinkdb.practice.infra.lib.rethinkdb.RethinkDBConnector
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NotificationDao(
    private val connector: RethinkDBConnector
) {
    fun findById(id: String): NotificationRecord? =
        getTable()
            .filterById(id)
            .let { connector.run(it) }
            .let { result ->
                if (result.hasNext()) {
                    result.first()
                } else {
                    null
                }
            }
            ?.let { NotificationTable.toRecord(it) }

    fun findAll(): List<NotificationRecord> =
        getTable()
            .let { connector.run(it) }
            .let { result ->
                result.map { NotificationTable.toRecord(it) }
            }

    fun register(notificationRecord: NotificationRecord) {
        val table = getTable()
        connector.rethinkDB
            .hashMap(NotificationTable.Columns.NotificationId.columnName, notificationRecord.id)
            .with(NotificationTable.Columns.TargetTodoId.columnName, notificationRecord.targetTodoId)
            .with(NotificationTable.Columns.ModificationTypeCode.columnName, notificationRecord.modifiedTypeCode)
            .with(NotificationTable.Columns.Before.columnName, notificationRecord.before)
            .with(NotificationTable.Columns.After.columnName, notificationRecord.after)
            .let { table.insert(it) }
            .let { connector.run(it) }
    }

    private fun getTable(): Table =
        connector.getNotification()

    private fun Table.filterById(id: String) =
        this.filter { rows -> rows.g(NotificationTable.Columns.NotificationId.columnName).eq(id) }
}

@Component
class NotificationEventRegister(
    private val notificationDao: NotificationDao,
    private val connector: RethinkDBConnector
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @OptIn(DelicateCoroutinesApi::class)
    fun register() =
        connector.getTodo().changes()
            .let { connector.run(it) }
            .let {
                GlobalScope.launch {
                    it.forEach {
                        kotlin.runCatching {
                            toRecord(it)
                                .let { record -> notificationDao.register(record) }
                        }.onFailure {
                            logger.error("Failed sync.", it)
                        }
                    }
                }
            }

    private fun toRecord(feed: Map<*, *>): NotificationRecord {
        val newValueMap = feed["new_val"] as Map<*, *>?
        val oldValueMap = feed["old_val"] as Map<*, *>?

        val newValue = newValueMap
            ?.let { toChangedContentRecord(it) }
        val oldValue = oldValueMap
            ?.let { toChangedContentRecord(it) }

        val targetTodoId = (newValue?.todoId)
            ?: (oldValue?.todoId)
            ?: throw IllegalStateException("New or old are required")

        return NotificationRecord(
            id = UUID.randomUUID().toString(),
            targetTodoId = targetTodoId,
            modifiedTypeCode = toType(newValue?.record, oldValue?.record).code,
            before = oldValue?.record,
            after = newValue?.record
        )
    }

    private fun toChangedContentRecord(feed: Map<*, *>): ContentRecordWithId =
        NotificationContentRecord(
            title = requireNotNull(feed[TodoTable.Columns.Title.columnName]) as String,
            deadLine = requireNotNull(feed[TodoTable.Columns.DeadLine.columnName]) as String,
            started = requireNotNull(feed[TodoTable.Columns.Started.columnName]) as Boolean,
            finished = requireNotNull(feed[TodoTable.Columns.Finished.columnName]) as Boolean,
        )
            .let {
                ContentRecordWithId(
                    todoId = requireNotNull(feed[TodoTable.Columns.TodoId.columnName]) as String,
                    record = it
                )
            }

    private fun toType(
        new: NotificationContentRecord?,
        old:NotificationContentRecord?
    ): ModifiedTypeValue {
        if (new == null) {
            return ModifiedTypeValue.Deleted
        }

        if (old == null) {
            return ModifiedTypeValue.Registered
        }

        if (old.started != new.started) {
            return ModifiedTypeValue.Started
        }

        if (old.finished != new.finished) {
            return ModifiedTypeValue.Finished
        }

        return ModifiedTypeValue.Changed
    }

    private class ContentRecordWithId(
        val todoId: String,
        val record: NotificationContentRecord,
    )
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
    val deadLine: String,
    val started: Boolean,
    val finished: Boolean
)

object NotificationTable {
    const val name = "notification"

    enum class Columns(val columnName: String) {
        NotificationId("notificationId"),
        TargetTodoId("targetTodoId"),
        ModificationTypeCode("modificationTypeCode"),
        Before("before"),
        After("after")
    }

    fun toRecord(result: Map<*, *>): NotificationRecord =
        NotificationRecord(
            id = requireNotNull(result[Columns.NotificationId.columnName]) as String,
            targetTodoId = requireNotNull(result[Columns.TargetTodoId.columnName]) as String,
            modifiedTypeCode = requireNotNull(result[Columns.ModificationTypeCode.columnName])
                .let { it as Long }
                .let { it.toInt() },
            before = result[Columns.Before.columnName]
                ?.let { it as Map<* ,*> }
                ?.let { toChangedContentRecord(it) },
            after = result[Columns.After.columnName]
                ?.let { it as Map<* ,*> }
                ?.let { toChangedContentRecord(it) }
        )

    private fun toChangedContentRecord(feed: Map<*, *>): NotificationContentRecord =
        NotificationContentRecord(
            title = requireNotNull(feed[TodoTable.Columns.Title.columnName]) as String,
            deadLine = requireNotNull(feed[TodoTable.Columns.DeadLine.columnName]) as String,
            started = requireNotNull(feed[TodoTable.Columns.Started.columnName]) as Boolean,
            finished = requireNotNull(feed[TodoTable.Columns.Finished.columnName]) as Boolean,
        )
}