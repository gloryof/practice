package jp.glory.istio.practice.app.person.usecase

import jp.glory.istio.practice.app.base.Either
import jp.glory.istio.practice.app.base.Left
import jp.glory.istio.practice.app.base.Right
import jp.glory.istio.practice.app.base.usecase.UseCaseErrorDetail
import jp.glory.istio.practice.app.base.usecase.UseCaseErrors
import jp.glory.istio.practice.app.person.domain.PersonId
import jp.glory.istio.practice.app.person.domain.PersonRepository

@UseCase
class PersonSearchUseCase(
    private val repository: PersonRepository
) {
    fun findById(
        id: String
    ): PersonResult? =
        repository.findById(
            id = PersonId(id)
        )
        ?.let {
            PersonResult (
                id = it.id.value,
                name = it.name,
                age = it.age
            )
        }
}

class PersonResult(
    val id: String,
    val name: String,
    val age: Int
)