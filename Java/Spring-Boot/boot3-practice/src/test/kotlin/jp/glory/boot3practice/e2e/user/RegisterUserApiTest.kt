package jp.glory.boot3practice.e2e.user

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebErrorDetailCode
import jp.glory.boot3practice.e2e.TestHelper
import jp.glory.boot3practice.e2e.addApplicationJsonHeader
import jp.glory.boot3practice.e2e.assertErrorResponse
import jp.glory.boot3practice.e2e.filter.ApiTestFilters
import jp.glory.boot3practice.e2e.filter.EnvironmentExtractor
import jp.glory.boot3practice.user.adaptor.web.RegisterUserApi
import org.hamcrest.Matchers
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class RegisterUserApiTest {
    private val basePath = EndpointConst.User.register

    @Nested
    inner class RegisterUser {

        @Test
        fun success() {
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createRequestUser())
            } When  {
                post(basePath)
            } Then  {
                statusCode(201)
                body("id", Matchers.not(Matchers.emptyString()))
            }
        }

        @Test
        fun methodNotAllowedByGet() {
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createRequestUser())
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
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createRequestUser())
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
            Given {
                filters(ApiTestFilters.notAuthorizedFilter())
                addApplicationJsonHeader()
                body(createRequestUser())
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

        private fun createRequestUser(): RegisterUserApi.UserRegisterRequest =
            LocalDateTime.now()
                .let {
                    RegisterUserApi.UserRegisterRequest(
                        name = "test-user-id-$it",
                        password = "test-password-$it",
                        birthDay = it.toLocalDate()
                    )
                }

    }
}