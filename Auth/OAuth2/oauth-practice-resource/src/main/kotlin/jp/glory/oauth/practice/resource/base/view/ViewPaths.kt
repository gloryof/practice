package jp.glory.oauth.practice.resource.base.view

object ViewBasePath {
    const val base = "/view"
}

object AuthViewPath {
    private const val basePath = "${ViewBasePath.base}/auth"
    const val login = "$basePath/login"
}

object UserViewPath {
    private const val basePath = "${ViewBasePath.base}/user"
    private const val hobbiesBase = "$basePath/hobby"

    const val registerUser = "$basePath/register"
    const val viewUser = "$basePath/view"

    const val viewHobbies = "$hobbiesBase/view"
    const val changeHobbies = "$hobbiesBase/change"
}