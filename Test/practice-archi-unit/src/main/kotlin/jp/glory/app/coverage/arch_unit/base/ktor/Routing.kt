package jp.glory.app.coverage.arch_unit.base.ktor

import com.github.michaelbull.result.mapBoth
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import jp.glory.app.coverage.arch_unit.base.adaptor.web.error.handleError
import jp.glory.app.coverage.arch_unit.product.adaptor.web.*

fun Application.configureRouting(
    productApi: ProductApi,
    memberApi: MemberApi,
    serviceApi: ServiceApi
) {
    routing {
        productRoute(productApi)
        memberRoute(memberApi)
        serviceRoute(serviceApi)
    }
}

private fun Routing.productRoute(
    productApi: ProductApi
) {
    route("/products") {
        get {
            productApi.findAll()
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
        post {
            val request = call.receive<RegisterProductRequest>()
            productApi.register(request)
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )

        }
        get("/{id}") {
            val id = call.parameters["id"] ?: ""
            productApi.findById(id)
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: ""
            val request = call.receive<UpdateProductRequest>()
            productApi.update(
                id = id,
                request = request
            )
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
    }
}

private fun Routing.memberRoute(
    memberApi: MemberApi
) {
    route("/members") {
        get {
            memberApi.findAll()
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: ""
            memberApi.findById(id)
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
    }

}

private fun Routing.serviceRoute(
    serviceApi: ServiceApi
) {
    route("/services") {
        get {
            serviceApi.findAll()
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: ""
            serviceApi.findById(id)
                .mapBoth(
                    success = { call.respond(it) },
                    failure = { call.handleError(it) }
                )
        }
    }

}