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
    private const val SUMMARY_MESSAGE_NOT_FOUND = "Product is not found."
    private const val SUMMARY_MESSAGE_BY_SPEC_ERROR = "Update event is not satisfy spec."
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
                message = SUMMARY_MESSAGE_BY_SPEC_ERROR,
                details = details
            )
        )

    private fun createNotFoundError(
        event: UpdateProductEvent,
    ): UseCaseNotFoundError =
        UseCaseNotFoundError(
            message = SUMMARY_MESSAGE_NOT_FOUND,
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
