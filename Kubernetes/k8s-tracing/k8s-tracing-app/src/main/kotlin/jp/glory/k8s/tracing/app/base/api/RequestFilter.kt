package jp.glory.k8s.tracing.app.base.api

import brave.Span
import brave.Tracer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestFilter(
    private val tracer: Tracer,
    private val config: TraceConfig
) : OncePerRequestFilter() {
    private val filterLogger: Logger = LoggerFactory.getLogger(RequestFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain) {

        tracer.currentSpan()?.let{ addCustomTag(it) }
        kotlin.runCatching {
            chain.doFilter(request, response)
        }
            .fold(
                onSuccess = { loggingAccessResult(request, response) },
                onFailure = { loggingError(request, response) }
            )
    }

    private fun addCustomTag(span: Span) {
        span.tag("custom.k8s.nodeName", config.nodeName)
        span.tag("custom.k8s.nameSpace", config.nameSpace)
        span.tag("custom.k8s.podName", config.podName)
        span.tag("custom.k8s.podIp", config.podIp)
    }

    private fun loggingAccessResult(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val log = AccessLog(request,response).output()
        when {
            response.status < 400 -> filterLogger.info(log)
            response.status < 500 -> filterLogger.warn(log)
            else -> filterLogger.error(log)
        }
    }

    private fun loggingError(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val log = AccessLog(request,response).output()
        filterLogger.error(log)
    }

    private class AccessLog(
        val remoteHost: String,
        val remoteUser: String,
        val requestMethod: String,
        val requestURL: String,
        val status: Int = 500
    ) {
        constructor(
            request: HttpServletRequest,
            response: HttpServletResponse
        ) :
                this(
                    remoteHost = request.remoteHost ?: "-",
                    remoteUser = request.remoteUser ?: "-",
                    requestMethod = request.method,
                    requestURL = request.requestURI,
                    status = response.status
                )

        fun output(): String =
            "$remoteHost $remoteUser \"$requestMethod $requestURL\" $status"
    }
}