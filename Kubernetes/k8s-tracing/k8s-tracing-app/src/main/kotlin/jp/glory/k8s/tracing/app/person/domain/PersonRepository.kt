package jp.glory.k8s.tracing.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

