package jp.glory.k8s.logging.app.person.domain

class Person(
    val id: PersonId,
    val name: String,
    val age: Int
)

inline class PersonId(val value: String)