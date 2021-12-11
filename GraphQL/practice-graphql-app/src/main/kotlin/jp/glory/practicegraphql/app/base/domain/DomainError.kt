package jp.glory.practicegraphql.app.base.domain

sealed class DomainError(
    open val message: String
)

class DomainUnknownError(
    override val message: String,
    val cause: Throwable
) : DomainError(message)

class SpecError(
    override val message: String,
    val details: List<SpecErrorDetail>
) : DomainError(message)

sealed class SpecErrorDetail {
    abstract fun toAttributes(): Map<String, String>
}

class DuplicateKeyErrorDetail(
    private val keyName: KeyName,
    private val inputValue: String,
) : SpecErrorDetail() {
    override fun toAttributes(): Map<String, String> =
        mapOf(
            "keyName" to keyName.name,
            "inputValue" to inputValue
        )

    enum class KeyName {
        ProductCode
    }
}
