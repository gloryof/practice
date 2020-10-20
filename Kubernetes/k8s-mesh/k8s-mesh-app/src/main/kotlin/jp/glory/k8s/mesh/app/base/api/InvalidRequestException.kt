package jp.glory.k8s.mesh.app.base.api

import jp.glory.k8s.mesh.app.base.usecase.UseCaseErrors

class InvalidRequestException(
    val summary: String,
    val errors: UseCaseErrors
) : RuntimeException(summary)