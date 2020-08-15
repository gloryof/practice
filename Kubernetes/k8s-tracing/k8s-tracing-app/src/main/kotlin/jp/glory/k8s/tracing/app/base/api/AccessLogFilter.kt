package jp.glory.k8s.tracing.app.base.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccessLogFilter : OncePerRequestFilter() {
    private val filterLogger: Logger = LoggerFactory.getLogger(AccessLogFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain) {

        kotlin.runCatching {
            chain.doFilter(request, response)
        }
            .fold(
                onSuccess = {
                    val log = AccessLog(request,response).output()
                    when {
                        response.status < 400 -> filterLogger.info(log)
                        response.status < 500 -> filterLogger.warn(log)
                        else -> filterLogger.error(log)
                    }
                },
                onFailure = {
                    val log = AccessLog(request,response).output()
                    filterLogger.error(log)
                }
            )
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