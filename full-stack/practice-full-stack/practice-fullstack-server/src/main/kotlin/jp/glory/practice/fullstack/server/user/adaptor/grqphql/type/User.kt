package jp.glory.practice.fullstack.server.user.adaptor.grqphql.type

import jp.glory.practice.fullstack.server.user.usecase.GetUser
import java.time.LocalDate

class User(
    val id: String,
    val name: String,
    val birthday: LocalDate
) {
    constructor(output: GetUser.Output) : this(
        id = output.id,
        name = output.name,
        birthday = output.birthday
    )
}