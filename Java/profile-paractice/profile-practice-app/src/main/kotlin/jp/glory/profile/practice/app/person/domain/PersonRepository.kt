package jp.glory.profile.practice.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

