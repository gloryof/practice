package jp.glory.k8s.logging.app.base.api

import jp.glory.k8s.logging.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)