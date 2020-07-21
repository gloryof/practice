package jp.glory.k8s.metrics.app.person.domain

interface PersonSaveEventRepository {
    fun register(event: PersonRegisterEvent): PersonId
    fun update(event: PersonUpdateEvent): PersonId
}