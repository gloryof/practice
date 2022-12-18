package jp.glory.boot3practice.e2e.auth

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jp.glory.boot3practice.auth.adaptor.web.AuthApi
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebAuthenticationFailedError
import jp.glory.boot3practice.base.adaptor.web.WebErrorDetailCode
import jp.glory.boot3practice.e2e.ExpectedErrorResponse
import jp.glory.boot3practice.e2e.TestHelper
import jp.glory.boot3practice.e2e.addApplicationJsonHeader
import jp.glory.boot3practice.e2e.assertErrorResponse
import jp.glory.boot3practice.e2e.filter.ApiTestFilters
import jp.glory.boot3practice.e2e.filter.EnvironmentExtractor
import jp.glory.boot3practice.e2e.filter.IdPassword
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.*

internal class AuthApiTest {
    private val basePath = EndpointConst.Auth.authenticate
    @Nested
    inner class Authenticate {
        @Test
        fun success() {
            val idPassword = EnvironmentExtractor.getIdPassword()
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createValidUserRequest(idPassword))
            } When  {
                post(basePath)
            } Then  {
                statusCode(200)
                body("token", not(emptyString()))
            }
        }

        @Test
        fun invalidUser() {
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createInvalidUserRequest())
            } When  {
                post(basePath)
            } Then  {
                val errorDetail = WebAuthenticationFailedError
                assertErrorResponse(
                    ExpectedErrorResponse(
                        errorDetail = errorDetail,
                        targetPath = basePath
                    )
                )
            }
        }

        @Test
        fun methodNotAllowedByGet() {
            val idPassword = EnvironmentExtractor.getIdPassword()
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createValidUserRequest(idPassword))
            } When  {
                get(basePath)
            } Then  {
                assertErrorResponse(
                    TestHelper.createErrorResponseFromDetailCode(
                        detailCode = WebErrorDetailCode.ERD404001,
                        targetPath = basePath
                    )
                )
            }
        }

        @Test
        fun methodNotAllowedByPut() {
            val idPassword = EnvironmentExtractor.getIdPassword()
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createValidUserRequest(idPassword))
            } When  {
                put(basePath)
            } Then  {
                assertErrorResponse(
                    TestHelper.createErrorResponseFromDetailCode(
                        detailCode = WebErrorDetailCode.ERD404001,
                        targetPath = basePath
                    )
                )
            }
        }

        @Test
        fun methodNotAllowedByDelete() {
            val idPassword = EnvironmentExtractor.getIdPassword()
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createValidUserRequest(idPassword))
            } When  {
                delete(basePath)
            } Then  {
                assertErrorResponse(
                    TestHelper.createErrorResponseFromDetailCode(
                        detailCode = WebErrorDetailCode.ERD404001,
                        targetPath = basePath
                    )
                )
            }
        }

        private fun createValidUserRequest(idPassword: IdPassword): AuthApi.AuthRequest =
            AuthApi.AuthRequest(
                id = idPassword.userId,
                password = idPassword.password
            )

        private fun createInvalidUserRequest(): AuthApi.AuthRequest =
            AuthApi.AuthRequest(
                id = "invalid-user",
                password = "invalid-password"
            )
    }
}