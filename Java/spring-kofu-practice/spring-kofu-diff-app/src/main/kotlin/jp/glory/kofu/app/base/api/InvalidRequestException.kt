package jp.glory.kofu.app.base.api

import jp.glory.kofu.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)