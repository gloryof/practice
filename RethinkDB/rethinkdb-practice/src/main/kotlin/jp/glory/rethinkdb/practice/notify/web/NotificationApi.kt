package jp.glory.rethinkdb.practice.notify.web

import jp.glory.rethinkdb.practice.base.web.WebApi
import jp.glory.rethinkdb.practice.base.web.WebApiPath
import jp.glory.rethinkdb.practice.notify.usecase.FindNotificationUseCase
import jp.glory.rethinkdb.practice.notify.usecase.ModifiedContentDetail
import jp.glory.rethinkdb.practice.notify.usecase.NotificationDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate

@WebApi
@RequestMapping(WebApiPath.Notifications)
class NotificationApi(
    private val notificationUseCase: FindNotificationUseCase
) {
    @GetMapping
    fun getAll(): ResponseEntity<NotificationsResponse> =
        notificationUseCase
            .findAll()
            .let { toNotificationsResponse(it) }
            .let { ResponseEntity.ok(it) }

    private fun toNotificationsResponse(
        output: FindNotificationUseCase.Output
    ): NotificationsResponse =
        output.details
            .map { toDetailResponse(it) }
            .let { NotificationsResponse(it) }

    private fun toDetailResponse(
        detail: NotificationDetail
    ): NotificationDetailResponse =
        NotificationDetailResponse(
            targetTodoId = detail.targetTodoId,
            modifiedType = detail.modifiedType.name,
            before = detail.before?.let { toDetailContentResponse(it) },
            after = detail.after?.let { toDetailContentResponse(it) },
        )

    private fun toDetailContentResponse(
        detail: ModifiedContentDetail
    ): ModifiedContentDetailResponse =
        ModifiedContentDetailResponse(
            title = detail.title,
            deadLine = detail.deadLine,
            status = detail.status.name
        )
}

class NotificationsResponse(
    val details: List<NotificationDetailResponse>
)

class NotificationDetailResponse(
    val targetTodoId: String,
    val modifiedType: String,
    val before: ModifiedContentDetailResponse?,
    val after: ModifiedContentDetailResponse?
)

class ModifiedContentDetailResponse(
    val title: String,
    val deadLine: LocalDate,
    val status: String,
)