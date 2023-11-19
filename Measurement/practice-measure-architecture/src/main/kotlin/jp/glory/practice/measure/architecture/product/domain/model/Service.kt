package jp.glory.practice.measure.architecture.product.domain.model

data class Service(
    val id: ServiceID,
    val name: ServiceName,
    val kind: ServiceKind
)

@JvmInline
value class ServiceID(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class ServiceName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 50)
    }
}

enum class ServiceKind {
    Finance,
    Entertainment,
    HealthCare
}