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

class ApiTestAuthFilter() : AuthFilter {
    override fun filter(
        requestSpec: FilterableRequestSpecification?,
        responseSpec: FilterableResponseSpecification?,
        ctx: FilterContext?
    ): Response {
        if (requestSpec == null || ctx == null) {
            throw IllegalStateException("Required parameter is null")
        }

        val csrfTokenHeader = getCsrfTokenHeader(requestSpec)

        requestSpec.header(csrfTokenHeader)
        requestSpec.header(getAuthorizationToken(requestSpec, csrfTokenHeader))

        return ctx.next(requestSpec, responseSpec)
    }

    private fun getCsrfTokenHeader(
        requestSpec: FilterableRequestSpecification
    ): Header {
        val baseUrl = requestSpec.baseUri ?: throw IllegalStateException("Base url is null")
        return post("$baseUrl/csrf/token")
            ?.body
            ?.asString()
            ?.let { Header("X-CSRF-TOKEN", it) }
            ?: throw IllegalStateException("CSRF token is null")
    }

    private fun getAuthorizationToken(
        requestSpec: FilterableRequestSpecification,
        csrfTokenHeader: Header
    ): Header {
        val idPassword = EnvironmentExtractor.getIdPassword()
        val baseUrl = requestSpec.baseUri ?: throw IllegalStateException("Base url is null")
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
            .post("$baseUrl/authenticate")
            ?.body
            ?.`as`(AuthenticateUserApi.Response::class.java)
            ?.let { Header("Authorization", "Bearer ${it.tokenValue}") }
            ?: throw IllegalStateException("Authentication token is null")
    }
}