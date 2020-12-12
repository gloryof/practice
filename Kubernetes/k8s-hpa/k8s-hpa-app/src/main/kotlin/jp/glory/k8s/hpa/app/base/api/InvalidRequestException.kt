package jp.glory.k8s.hpa.app.base.api

import jp.glory.k8s.hpa.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)