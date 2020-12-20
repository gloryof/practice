package jp.glory.profile.practice.app.base.usecase

import jp.glory.profile.practice.app.base.domain.SpecErrorDetail
import jp.glory.profile.practice.app.base.domain.SpecErrors

class UseCaseErrors(
    val errors: List<UseCaseErrorDetail>
) {
    constructor(
        specErrors: SpecErrors
    ) : this(
        errors = specErrors.toList()
            .map { UseCaseErrorDetail(it) }
    )
}

class UseCaseErrorDetail(
    val itemName: String,
    val messages: List<String>
) {
    constructor(
        specErrorDetail: SpecErrorDetail
    ) : this(
        itemName = specErrorDetail.itemName,
        messages = specErrorDetail.getMessages()
    )

}