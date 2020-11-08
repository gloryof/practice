package jp.glory.istio.practice.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

