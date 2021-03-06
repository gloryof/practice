package jp.glory.k8s.hpa.app.person.usecase

import jp.glory.k8s.hpa.app.base.Either
import jp.glory.k8s.hpa.app.base.Left
import jp.glory.k8s.hpa.app.base.Right
import jp.glory.k8s.hpa.app.base.usecase.UseCaseErrorDetail
import jp.glory.k8s.hpa.app.base.usecase.UseCaseErrors
import jp.glory.k8s.hpa.app.person.domain.PersonId
import jp.glory.k8s.hpa.app.person.domain.PersonRepository

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