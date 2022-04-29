package jp.glory.ci.cd.practice.app.it.rest_assured.filter

import io.restassured.filter.FilterContext
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification
import io.restassured.spi.AuthFilter
import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.http.Headers
import jp.glory.ci.cd.practice.app.auth.web.AuthenticateUserApi
import jp.glory.ci.cd.practice.app.it.util.EnvironmentExtractor

class ApiTestAuthFilter : AuthFilter {
    override fun filter(
        requestSpec: FilterableRequestSpecification?,
        responseSpec: FilterableResponseSpecification?,
        ctx: FilterContext?
    ): Response {
        if (requestSpec == null || ctx == null) {
            throw IllegalStateException("Required parameter is null")
        }
        if (!RestAssuredFilterContext.isSatisfied()) {
            setHeaderToContext()
        }
        RestAssuredFilterContext.registerToRequestSpecification(requestSpec)

        return ctx.next(requestSpec, responseSpec)
    }

    private fun setHeaderToContext() {
        val csrfTokenHeader = getCsrfTokenHeader()
        val authTokenHeader = getAuthorizationToken(csrfTokenHeader)

        RestAssuredFilterContext.setCsrfTokenHeader(csrfTokenHeader)
        RestAssuredFilterContext.setAuthTokenHeader(authTokenHeader)
    }

    private fun getCsrfTokenHeader(): Header {
        val baseUrl = EnvironmentExtractor.getTargetUrl()
        val targetPort = EnvironmentExtractor.getTargetPort()
        return post("$baseUrl:$targetPort/csrf/token")
            ?.body
            ?.asString()
            ?.let { Header("X-CSRF-TOKEN", it) }
            ?: throw IllegalStateException("CSRF token is null")
    }

    private fun getAuthorizationToken(
        csrfTokenHeader: Header
    ): Header {
        val idPassword = EnvironmentExtractor.getIdPassword()
        val baseUrl = EnvironmentExtractor.getTargetUrl()
        val targetPort = EnvironmentExtractor.getTargetPort()
        return given()
            .headers(
                Headers(
                    csrfTokenHeader,
                    Header("Content-Type", ContentType.JSON.contentTypeStrings[0])
                )
            )
            .body(
                AuthenticateUserApi.Request(
                    userId = idPassword.userId,
                    password = idPassword.password
                )
            )
            .post("$baseUrl:$targetPort/authenticate")
            ?.body
            ?.`as`(AuthenticateUserApi.Response::class.java)
            ?.let { Header("Authorization", "Bearer ${it.tokenValue}") }
            ?: throw IllegalStateException("Authentication token is null")
    }
}