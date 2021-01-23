package jp.glory.practice.bench.app.base.api

import jp.glory.practice.bench.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)