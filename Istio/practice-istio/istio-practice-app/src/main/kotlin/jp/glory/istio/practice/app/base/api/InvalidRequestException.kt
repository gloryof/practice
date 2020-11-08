package jp.glory.istio.practice.app.base.api

import jp.glory.istio.practice.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)