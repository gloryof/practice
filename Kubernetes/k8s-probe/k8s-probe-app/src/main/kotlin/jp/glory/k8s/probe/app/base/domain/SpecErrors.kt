package jp.glory.k8s.probe.app.base.domain

class SpecErrors() {
    private val errors: MutableList<SpecErrorDetail> = mutableListOf()

    fun add(detail :SpecErrorDetail) = errors.add(detail)
    fun toList(): List<SpecErrorDetail> = errors.toList()
    fun hasError(): Boolean = errors.isNotEmpty()
}

class SpecErrorDetail(
    val itemName: String
) {
    private val messages: MutableList<String> = mutableListOf()

    fun getMessages(): List<String> = messages.toList()

    fun hasError() = messages.isNotEmpty()

    fun addRequiredError() = add("$itemName is required.Input $itemName.")
    fun addMaxLengthError(maxLength: Int) =
        add("$itemName is over $maxLength.Input $itemName length less than $maxLength.")

    fun add(message: String) = messages.add(message)
}