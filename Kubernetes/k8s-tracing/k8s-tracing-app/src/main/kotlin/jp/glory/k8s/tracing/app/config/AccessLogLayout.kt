package jp.glory.k8s.tracing.app.config

import brave.propagation.TraceContext
import ch.qos.logback.access.spi.AccessEvent
import ch.qos.logback.core.LayoutBase
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AccessLogLayout : LayoutBase<AccessEvent>() {
    override fun doLayout(event: AccessEvent?): String {
        val date = createDateTime(event)
        val dateTimePart = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm:ss.SSS")
            .format(date)
        val pid = ProcessHandle.current().pid()
        val request = createRequestPart(event)
        val requestPart = request.output()
        val tracePart = event
                ?.request
                ?.let { it.getAttribute("brave.propagation.TraceContext") as TraceContext }
                ?.let { createTracePart(it) }
                ?.output()
                ?: ""
        val logLevel = getLogLevel(request.status)

        return "$dateTimePart  ${logLevel} $tracePart $pid --- $requestPart"
    }

    private fun createDateTime(event: AccessEvent?): ZonedDateTime =
        event
            ?.let {
                Instant
                    .ofEpochMilli(it.timeStamp)
                    .atZone(ZoneId.systemDefault())
            }
            ?: ZonedDateTime.now()

    private fun createRequestPart(event: AccessEvent?): RequestPart =
        event
            ?.let {
                RequestPart(
                    threadName = it.threadName ?: "",
                    remoteHost = it.remoteHost ?: "-",
                    remoteUser = it.remoteUser ?: "-",
                    requestURL = it.requestURL,
                    status = it.statusCode,
                    contentLength = it.contentLength
                )
            }
            ?: RequestPart()

    private fun createTracePart(context: TraceContext?): TracePart =
        context
            ?.let {
                TracePart(
                    traceId = it.traceIdString() ?: "",
                    parentId = it.parentIdString() ?: "",
                    spanId = it.spanIdString() ?: "",
                    localRoot = it.isLocalRoot
                )
            }
            ?: TracePart()

    private class RequestPart(
        val threadName: String = "",
        val remoteHost: String = "-",
        val remoteUser: String = "",
        val requestURL: String = "",
        val status: Int = 500,
        val contentLength: Long = -1
    ) {
        fun output(): String =
            "[$threadName] $remoteHost $remoteUser \"$requestURL\" $status $contentLength"
    }

    private class TracePart(
        val traceId: String = "",
        val parentId: String = "",
        val spanId: String = "",
        val localRoot: Boolean = false
    ) {
        fun output(): String =
            "[$parentId,$traceId,$spanId,$localRoot]"
    }

    private fun getLogLevel(status: Int): String =
        when {
            status < 400 -> "INFO"
            status < 500 -> "WARN"
            else -> "ERROR"
        }
}