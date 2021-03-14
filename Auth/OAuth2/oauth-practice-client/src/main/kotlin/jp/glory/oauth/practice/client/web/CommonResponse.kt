package jp.glory.oauth.practice.client.web

data class UserViewInfo(
    val user: UserInfo,
    val mode: Mode
)

data class UserInfo(
    val id: String,
    val name: String,
    val hobbies: List<String>
)

enum class Mode {
    CODE,
    IMPLICIT,
    OWNER,
    CIENT
}