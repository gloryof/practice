package jp.glory.k8s.tracing.app.base.api

import jp.glory.k8s.tracing.app.base.usecase.UseCaseErrorDetail
import jp.glory.k8s.tracing.app.base.usecase.UseCaseErrors

class ErrorResponse(
    val summary: String,
    val errors: List<ErrorDetailResponse>
) {
    constructor(
        summary: String,
        errors: UseCaseErrors
    ) : this(
        summary = summary,
        errors = errors.errors.map { ErrorDetailResponse(it) }
    )
}

class ErrorDetailResponse(
    val itemName: String,
    val messages: List<String>
) {
    constructor(
        detail: UseCaseErrorDetail
    ) : this(
        itemName = detail.itemName,
        messages = detail.messages
    )
}