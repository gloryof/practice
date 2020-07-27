package jp.glory.k8s.probe.app.base.api

import jp.glory.k8s.probe.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)