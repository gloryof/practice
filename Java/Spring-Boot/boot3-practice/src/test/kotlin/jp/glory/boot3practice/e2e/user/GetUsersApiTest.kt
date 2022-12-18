package jp.glory.boot3practice.e2e.user

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.adaptor.web.WebErrorDetailCode
import jp.glory.boot3practice.base.adaptor.web.WebTargetNotFoundErrorDetail
import jp.glory.boot3practice.e2e.ExpectedErrorResponse
import jp.glory.boot3practice.e2e.TestHelper
import jp.glory.boot3practice.e2e.assertErrorResponse
import jp.glory.boot3practice.e2e.filter.ApiTestFilters
import jp.glory.boot3practice.e2e.filter.EnvironmentExtractor
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.*


internal class GetUsersApiTest {
    private val basePath = EndpointConst.User.user
    @Nested
    inner class GetById {
        @Test
        fun success() {
            val userId = EnvironmentExtractor.getTestUserId()
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When  {
                get("$basePath/$userId")
            } Then  {
                statusCode(200)
                body("name", equalTo("sample-user"))
                body("birth_day", equalTo("1986-12-17"))
            }
        }

        @Test
        fun notFound() {
            val userId = "not-found-user"
            val targetPath = "$basePath/$userId"
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When {
                get(targetPath)
            } Then {
                assertErrorResponse(
                    ExpectedErrorResponse(
                        errorDetail = WebTargetNotFoundErrorDetail(
                            resourceName = "User",
                            idValue = userId
                        ),
                        targetPath = targetPath
                    )
                )
            }
        }

        @Test
        fun methodNotAllowedByPost() {
            val userId = EnvironmentExtractor.getTestUserId()
            val targetPath = "$basePath/$userId"
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When  {
                post(targetPath)
            } Then  {
                assertErrorResponse(
                    TestHelper.createErrorResponseFromDetailCode(
                        detailCode = WebErrorDetailCode.ERD404001,
                        targetPath = targetPath
                    )
                )
            }
        }

        @Test
        fun methodNotAllowedByPut() {
            val userId = EnvironmentExtractor.getTestUserId()
            val targetPath = "$basePath/$userId"
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When  {
                put(targetPath)
            } Then  {
                assertErrorResponse(
                    TestHelper.createErrorResponseFromDetailCode(
                        detailCode = WebErrorDetailCode.ERD404001,
                        targetPath = targetPath
                    )
                )
            }
        }

        @Test
        fun methodNotAllowedByDelete() {
            val userId = EnvironmentExtractor.getTestUserId()
            val targetPath = "$basePath/$userId"
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When  {
                delete(targetPath)
            } Then  {
                assertErrorResponse(
                    TestHelper.createErrorResponseFromDetailCode(
                        detailCode = WebErrorDetailCode.ERD404001,
                        targetPath = targetPath
                    )
                )
            }
        }
    }

    @Nested
    inner class GetUsers {
        @Test
        fun success() {
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When {
                get(basePath)
            } Then {
                statusCode(200)
                body("users.size()", greaterThanOrEqualTo(1))
                body("users[0].name", equalTo("sample-user"))
                body("users[0].birth_day", equalTo("1986-12-17"))
            }
        }

        @Test
        fun methodNotAllowedByPost() {
            Given {
                filters(ApiTestFilters.authorizedFilter())
            } When  {
                post(basePath)
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
                filters(ApiTestFilters.authorizedFilter())
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
                filters(ApiTestFilters.authorizedFilter())
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
    }
}