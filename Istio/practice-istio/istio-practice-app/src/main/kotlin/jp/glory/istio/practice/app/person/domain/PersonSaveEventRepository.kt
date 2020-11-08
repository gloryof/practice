package jp.glory.istio.practice.app.person.domain

interface PersonSaveEventRepository {
    fun register(event: PersonRegisterEvent): PersonId
    fun update(event: PersonUpdateEvent): PersonId
}