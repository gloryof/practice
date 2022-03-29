package jp.glory.ci.cd.practice.app.user.domain.model

data class RegisterUserEvent(
    val userId: UserId,
    val givenName: GivenName,
    val familyName: FamilyName,
    val password: Password
)