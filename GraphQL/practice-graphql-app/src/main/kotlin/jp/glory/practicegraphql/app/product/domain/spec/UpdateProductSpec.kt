package jp.glory.practicegraphql.app.product.domain.spec

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DuplicateKeyErrorDetail
import jp.glory.practicegraphql.app.base.domain.SpecError
import jp.glory.practicegraphql.app.base.domain.SpecErrorDetail
import jp.glory.practicegraphql.app.base.usecase.UseCaseNotFoundError
import jp.glory.practicegraphql.app.product.domain.model.UpdateProductEvent
import jp.glory.practicegraphql.app.product.domain.model.UpdateProductPreCheckResult

object UpdateProductSpec {
    fun validate(
        event: UpdateProductEvent,
        preCheckResult: UpdateProductPreCheckResult
    ): Result<Unit, SpecError> =
        if (preCheckResult.hasSameCode()) {
            createError(
                details = listOf(createDuplicateError(event))
            )
        } else {
            Ok(Unit)
        }

    private fun createError(
        details: List<SpecErrorDetail>
    ): Err<SpecError> =
        Err(
            SpecError(
                details = details
            )
        )

    private fun createNotFoundError(
        event: UpdateProductEvent,
    ): UseCaseNotFoundError =
        UseCaseNotFoundError(
            resourceName = UseCaseNotFoundError.ResourceName.Product,
            idValue = event.id.value
        )

    private fun createDuplicateError(
        event: UpdateProductEvent,
    ): DuplicateKeyErrorDetail =
        DuplicateKeyErrorDetail(
            keyName = DuplicateKeyErrorDetail.KeyName.ProductCode,
            inputValue = event.code.value
        )
}
