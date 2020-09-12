package jp.glory.kofu.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

