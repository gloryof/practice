package jp.glory.boot3practice.e2e

import io.restassured.http.Header
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import jp.glory.boot3practice.base.adaptor.web.WebErrorDetail
import jp.glory.boot3practice.base.adaptor.web.WebErrorDetailCode
import jp.glory.boot3practice.base.adaptor.web.WebGenericErrorDetail
import org.springframework.http.HttpStatus
import org.hamcrest.Matchers.*

object TestHelper {
    fun createErrorResponseFromDetailCode(
        detailCode: WebErrorDetailCode,
        targetPath: String
    ): ExpectedErrorResponse =
        when (detailCode) {
            WebErrorDetailCode.ERD400001 -> createGenericErrorDetail(HttpStatus.BAD_REQUEST)
            WebErrorDetailCode.ERD401001 -> createGenericErrorDetail(HttpStatus.UNAUTHORIZED)
            WebErrorDetailCode.ERD404001 -> createGenericErrorDetail(HttpStatus.NOT_FOUND)
            WebErrorDetailCode.ERD500001 -> createGenericErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR)
            else -> throw IllegalArgumentException("$detailCode is not supported")
        }
            .let {
                ExpectedErrorResponse(
                    errorDetail = it,
                    targetPath = targetPath
                )
            }

    private fun createGenericErrorDetail(
        httpStatus: HttpStatus
    ): WebGenericErrorDetail =
        WebGenericErrorDetail.createGenericErrorDetail(httpStatus)
}

fun ValidatableResponse.assertErrorResponse(expected: ExpectedErrorResponse) {
    val errorDetail = expected.errorDetail
    body("status_code", equalTo(errorDetail.getHttpStatus().name))
    body("detail_message_code", equalTo(errorDetail.getErrorDetailCodeValue()))

    val messageArgument = errorDetail.getErrorDetailMessageArgument()
    if (messageArgument.isNotEmpty()) {
        messageArgument.forEachIndexed { idx, value ->
            body("detail_message_arguments[$idx]", equalTo(value))
        }
    }

    body("title_message_code", equalTo(errorDetail.getErrorCodeValue()))
    body("body.type", equalTo(expected.targetPath))
    body("body.title", equalTo(expected.errorDetail.getErrorMessage()))
    body("body.status", equalTo(errorDetail.getHttpStatus().value()))
    body("body.detail", equalTo(errorDetail.getErrorDetailMessage()))
}

fun RequestSpecification.addApplicationJsonHeader() {
    header(Header("Content-Type", "application/json"))
}

class ExpectedErrorResponse(
    val errorDetail: WebErrorDetail,
    val targetPath: String
)