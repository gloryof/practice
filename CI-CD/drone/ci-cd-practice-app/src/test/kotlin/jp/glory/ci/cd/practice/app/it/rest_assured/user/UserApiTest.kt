package jp.glory.ci.cd.practice.app.it.rest_assured.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jp.glory.ci.cd.practice.app.it.rest_assured.RestAssuredHelper
import jp.glory.ci.cd.practice.app.it.rest_assured.RestAssuredTest
import jp.glory.ci.cd.practice.app.it.rest_assured.filter.ApiTestFilters
import jp.glory.ci.cd.practice.app.it.util.EnvironmentExtractor
import jp.glory.ci.cd.practice.app.it.util.OuterFileExtractor
import jp.glory.ci.cd.practice.app.user.web.request.RegisterUserRequest
import jp.glory.ci.cd.practice.app.user.web.request.UpdateUserRequest
import jp.glory.ci.cd.practice.app.user.web.response.RegisterUserResponse
import jp.glory.ci.cd.practice.app.user.web.response.UpdateUserResponse
import jp.glory.ci.cd.practice.app.user.web.response.UserResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID

@RestAssuredTest
internal class UserApiTest {
    private val basePath = "/user"

    @Nested
    inner class GetById {
        @Test
        fun success() {
            val userId = EnvironmentExtractor.getTestUserId()
            val expected = OuterFileExtractor.readString("$basePath/system-user.json")
                .let { jacksonObjectMapper().readValue(it, UserResponse::class.java) }
            val actual = Given {
                filters(ApiTestFilters.requiredFilters())
            } When {
                get("$basePath/$userId")
            } Then {
                statusCode(200)
            } Extract {
                `as`(UserResponse::class.java)
            }

            assertEquals(actual, expected)
        }

        @Test
        fun notFound() {
            Given {
                filters(ApiTestFilters.requiredFilters())
            } When {
                get("$basePath/not-found-user")
            } Then {
                statusCode(404)
            }
        }
    }

    @Nested
    inner class Resister {
        @Test
        fun success() {
            val actual = Given {
                filters(ApiTestFilters.requiredFilters())
                body(createRegisterRequest())
                headers(RestAssuredHelper.createRudHeaders())
            } When {
                post(basePath)
            } Then  {
                statusCode(201)
            } Extract {
                `as`(RegisterUserResponse::class.java)
            }

            assertTrue(actual.userId.isNotEmpty())
        }

        @Test
        fun badRequest() {
            val request = RegisterUserRequest(
                givenName = "",
                familyName = "test-family-name",
                password = "test-password-123456"
            )
            Given {
                filters(ApiTestFilters.requiredFilters())
                body(request)
                headers(RestAssuredHelper.createRudHeaders())
            } When {
                post(basePath)
            } Then  {
                statusCode(400)
            }
        }
    }

    @Nested
    inner class Update {
        @Test
        fun success() {
            val targetId = EnvironmentExtractor.getUpdateUserId()
            val request = createUpdateRequest()

            val updateResul = Given {
                filters(ApiTestFilters.requiredFilters())
                body(request)
                headers(RestAssuredHelper.createRudHeaders())
            } When {
                put("$basePath/$targetId")
            } Then  {
                statusCode(200)
            } Extract {
                `as`(UpdateUserResponse::class.java)
            }

            assertEquals(targetId, updateResul.userId)

            val expected = UserResponse(
                userId = targetId,
                givenName = request.givenName,
                familyName = request.familyName
            )
            val actual = Given {
                filters(ApiTestFilters.requiredFilters())
            } When {
                get("/user/$targetId")
            } Then {
                statusCode(200)
            } Extract {
                `as`(UserResponse::class.java)
            }

            assertEquals(actual, expected)
        }

        @Test
        fun badRequest() {
            val targetId = EnvironmentExtractor.getUpdateUserId()
            val request = UpdateUserRequest(
                givenName = "",
                familyName = "test-family-name",
            )
            Given {
                filters(ApiTestFilters.requiredFilters())
                body(request)
                headers(RestAssuredHelper.createRudHeaders())
            } When {
                put("$basePath/$targetId")
            } Then  {
                statusCode(400)
            }
        }
    }

    @Nested
    inner class Delete {
        @Test
        fun success() {
            val registerResult = Given {
                filters(ApiTestFilters.requiredFilters())
                body(createRegisterRequest())
                headers(RestAssuredHelper.createRudHeaders())
            } When {
                post(basePath)
            } Then  {
                statusCode(201)
            } Extract {
                `as`(RegisterUserResponse::class.java)
            }
            val targetId = registerResult.userId

            Given {
                filters(ApiTestFilters.requiredFilters())
                headers(RestAssuredHelper.createRudHeaders())
            } When {
                delete("$basePath/$targetId")
            } Then  {
                statusCode(204)
            }

            Given {
                filters(ApiTestFilters.requiredFilters())
            } When {
                get("/user/$targetId")
            } Then {
                statusCode(404)
            }
        }
    }
    private fun createRegisterRequest(): RegisterUserRequest =
        RegisterUserRequest(
            givenName = "test-given-name",
            familyName = "test-family-name",
            password = "test-password-123456"
        )

    private fun createUpdateRequest(): UpdateUserRequest {
        val suffix = UUID.randomUUID().toString()
        return UpdateUserRequest(
            givenName = "given-name-$suffix",
            familyName = "family-name-$suffix"
        )
    }
}