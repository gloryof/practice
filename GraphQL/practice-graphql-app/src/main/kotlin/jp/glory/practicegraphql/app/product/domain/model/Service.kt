package jp.glory.practicegraphql.app.product.domain.model

data class Service(
    val id: ServiceID,
    val name: ServiceName,
    val kind: ServiceKind
)

@JvmInline
value class ServiceID(val value: String)

@JvmInline
value class ServiceName(val value: String)

enum class ServiceKind {
    Finance,
    Entertainment,
    HealthCare
}