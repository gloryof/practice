package jp.glory.k8s.logging.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

