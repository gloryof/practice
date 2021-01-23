package jp.glory.practice.bench.app.person.usecase

import jp.glory.practice.bench.app.base.Either
import jp.glory.practice.bench.app.base.Left
import jp.glory.practice.bench.app.base.Right
import jp.glory.practice.bench.app.base.usecase.UseCaseErrors
import jp.glory.practice.bench.app.person.domain.PersonRegisterEventSpec
import jp.glory.practice.bench.app.person.domain.PersonSaveEventRepository
import jp.glory.practice.bench.app.person.domain.PersonUpdateEventSpec

@UseCase
class PersonSaveUseCase(
    val repository: PersonSaveEventRepository
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