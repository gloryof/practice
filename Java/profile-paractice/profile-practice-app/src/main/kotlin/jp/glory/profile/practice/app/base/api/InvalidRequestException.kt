package jp.glory.profile.practice.app.base.api

import jp.glory.profile.practice.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)