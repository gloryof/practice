package jp.glory.practice.bench.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

