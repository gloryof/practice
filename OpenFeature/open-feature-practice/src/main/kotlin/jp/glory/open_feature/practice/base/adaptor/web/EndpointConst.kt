package jp.glory.open_feature.practice.base.adaptor.web

import org.springframework.http.HttpMethod

object EndpointConst {
    object User {
        const val user = "/api/users"
    }
    object Product {
        const val product = "/api/products"
        const val service = "/api/services"
        const val member = "/api/members"
    }
}

object EndPointSettings {
    private val settings = mapOf(
        EndpointConst.User.user to listOf(
            EndPointSetting(
                name = "Register User",
                method = HttpMethod.POST,
                key = "user.register"
            ),
        ),
        EndpointConst.Product.product to listOf(
            EndPointSetting(
                name = "Register Product",
                method = HttpMethod.POST,
                key = "product.register"
            )
        )
    )
    fun getSetting(
        path: String,
        method: String
    ): EndPointSetting? =
        settings[path]
            ?.let {
                it.findLast { setting -> setting.matchMethod(method) }
            }
}

class EndPointSetting(
    private val name: String,
    private val method: HttpMethod,
    private val key: String
) {
    fun matchMethod(methodName: String): Boolean =
        method.matches(methodName)

    fun getFeatureKey(): String = "practice.api.$key"
}