package jp.glory.k8s.metrics.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

