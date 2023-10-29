package jp.glory.boot3practice.base.adaptor.web

object EndpointConst {
    object Auth {
        const val authenticate = "/api/auth"
    }

    object User {
        const val register = "/api/register"
        const val bulkRegister = "/api/bulk-register"
        const val user = "/api/users"
    }
}