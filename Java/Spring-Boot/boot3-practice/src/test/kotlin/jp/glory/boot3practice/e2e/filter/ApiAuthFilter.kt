package jp.glory.boot3practice.e2e.filter

import io.restassured.RestAssured
import io.restassured.filter.FilterContext
import io.restassured.http.Header
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification
import io.restassured.spi.AuthFilter
import jp.glory.boot3practice.auth.adaptor.web.AuthApi
import jp.glory.boot3practice.base.adaptor.web.EndpointConst

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
        val authTokenHeader = getAuthorizationToken()

        RestAssuredFilterContext.setAuthTokenHeader(authTokenHeader)
    }

    private fun getAuthorizationToken(): Header {
        val idPassword = EnvironmentExtractor.getIdPassword()
        val targetUrl = EnvironmentExtractor.getTargetUrl()
        val targetPort = EnvironmentExtractor.getTargetPort()
        return RestAssured.given()
            .body(
                AuthApi.AuthRequest(
                    id = idPassword.userId,
                    password = idPassword.password
                )
            )
            .header(Header("Content-Type", "application/json"))
            .post("$targetUrl:$targetPort${EndpointConst.Auth.authenticate}")
            ?.body
            ?.`as`(AuthApi.AuthResponse::class.java)
            ?.let { Header("Authorization", "Bearer ${it.token}") }
            ?: throw IllegalStateException("Authentication token is null")
    }
}