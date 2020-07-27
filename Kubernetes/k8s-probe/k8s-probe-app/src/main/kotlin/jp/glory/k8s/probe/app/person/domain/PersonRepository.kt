package jp.glory.k8s.probe.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

