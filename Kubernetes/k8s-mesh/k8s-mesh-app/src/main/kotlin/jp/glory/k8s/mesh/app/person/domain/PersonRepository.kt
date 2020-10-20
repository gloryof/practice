package jp.glory.k8s.mesh.app.person.domain

interface PersonRepository {
    fun findById(id: PersonId): Person?
}

