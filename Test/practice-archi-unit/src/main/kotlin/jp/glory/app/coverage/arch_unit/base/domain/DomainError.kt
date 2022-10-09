package jp.glory.app.coverage.arch_unit.base.domain

sealed class DomainError

class DomainUnknownError(
    val cause: Throwable
) : DomainError()

class SpecError(
    val details: List<SpecErrorDetail>
) : DomainError()

sealed class SpecErrorDetail

class DuplicateKeyErrorDetail(
    val keyName: KeyName,
    val inputValue: String,
) : SpecErrorDetail() {
    enum class KeyName {
        ProductCode
    }
}
