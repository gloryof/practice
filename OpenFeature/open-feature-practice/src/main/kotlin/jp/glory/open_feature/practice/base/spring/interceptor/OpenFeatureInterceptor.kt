package jp.glory.open_feature.practice.base.spring.interceptor

import dev.openfeature.sdk.OpenFeatureAPI
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jp.glory.open_feature.practice.base.adaptor.web.EndPointSettings
import jp.glory.open_feature.practice.base.adaptor.web.WebNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

class OpenFeatureInterceptor(
    private val openFeatureAPI: OpenFeatureAPI
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (canUser(request)) {
            return super.preHandle(request, response, handler)
        } else {
            throw WebNotFoundException.create(
                resourceName = "Endpoint",
                idValue = request.servletPath
            )
        }
    }

    private fun canUser(
        request: HttpServletRequest
    ): Boolean =
        EndPointSettings.getSetting(
            path = request.servletPath,
            method = request.method
        )
            ?.let {
                openFeatureAPI.client
                    .getBooleanValue(it.getFeatureKey(), false)
            }
            ?: true
}