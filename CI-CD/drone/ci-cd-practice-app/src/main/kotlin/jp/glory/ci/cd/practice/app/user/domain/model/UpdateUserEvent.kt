package jp.glory.ci.cd.practice.app.user.domain.model

data class UpdateUserEvent(
    val userId: UserId,
    val givenName: GivenName,
    val familyName: FamilyName,
)