package jp.glory.kofu.app.person.usecase

import jp.glory.kofu.app.base.Either
import jp.glory.kofu.app.base.Left
import jp.glory.kofu.app.base.Right
import jp.glory.kofu.app.base.usecase.UseCaseErrors
import jp.glory.kofu.app.person.domain.PersonRegisterEventSpec
import jp.glory.kofu.app.person.domain.PersonSaveEventRepository
import jp.glory.kofu.app.person.domain.PersonUpdateEventSpec

class PersonSaveUseCase(
    private val repository: PersonSaveEventRepository
) {
    fun register(
        name: String?,
        age: Int?
    ): Either<UseCaseErrors, String> {
        return PersonRegisterEventSpec.validate(
            name = name,
            age = age
        ).mapBoth(
            right = { Right(repository.register(it).value) },
            left = { Left(UseCaseErrors(it)) }
        )
    }

    fun update(
        personId: String?,
        name: String?,
        age: Int?
    ): Either<UseCaseErrors, String> {
        return PersonUpdateEventSpec.validate(
            personId = personId,
            name = name,
            age = age
        ).mapBoth(
            right = { Right(repository.update(it).value) },
            left = { Left(UseCaseErrors(it)) }
        )
    }
}