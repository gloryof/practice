package jp.glory.k8s.hpa.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

