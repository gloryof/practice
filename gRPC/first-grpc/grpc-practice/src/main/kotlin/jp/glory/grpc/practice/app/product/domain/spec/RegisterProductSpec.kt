package jp.glory.grpc.practice.app.product.domain.spec

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.grpc.practice.base.domain.DuplicateKeyErrorDetail
import jp.glory.grpc.practice.base.domain.SpecError
import jp.glory.grpc.practice.base.domain.SpecErrorDetail
import jp.glory.grpc.practice.app.product.domain.model.RegisterProductEvent
import jp.glory.grpc.practice.app.product.domain.model.RegisterProductPreCheckResult

object RegisterProductSpec {
    private const val SUMMARY_MESSAGE = "Register product is not satisfy spec."
    fun validate(
        event: RegisterProductEvent,
        preCheckResult: RegisterProductPreCheckResult
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

    private fun createDuplicateError(
        event: RegisterProductEvent,
    ): DuplicateKeyErrorDetail =
        DuplicateKeyErrorDetail(
            keyName = DuplicateKeyErrorDetail.KeyName.ProductCode,
            inputValue = event.code.value
        )
}
