package jp.glory.ci.cd.practice.app.it.rest_assured.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jp.glory.ci.cd.practice.app.it.rest_assured.RestAssuredTest
import jp.glory.ci.cd.practice.app.it.rest_assured.filter.ApiTestFilters
import jp.glory.ci.cd.practice.app.it.util.EnvironmentExtractor
import jp.glory.ci.cd.practice.app.it.util.OuterFileExtractor
import jp.glory.ci.cd.practice.app.user.web.response.UserResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@RestAssuredTest
internal class UserApiTest {

    @Nested
    inner class GetById {
        @Test
        fun success() {
            val userId = EnvironmentExtractor.getTestUserId()
            val expected = OuterFileExtractor.readString("/user/system-user.json")
                .let { jacksonObjectMapper().readValue(it, UserResponse::class.java) }
            val actual = Given {
                filters(ApiTestFilters.requiredFilters())
            } When  {
                get("/user/$userId")
            } Then  {
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
            } When  {
                get("/user/not-found-user")
            } Then {
                statusCode(404)
            }
        }
    }

}