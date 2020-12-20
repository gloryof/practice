package jp.glory.profile.practice.app.person.domain

class Person(
    val id: PersonId,
    val name: String,
    val age: Int
)

inline class PersonId(val value: String)