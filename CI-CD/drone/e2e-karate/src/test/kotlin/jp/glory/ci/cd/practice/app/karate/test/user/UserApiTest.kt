package jp.glory.ci.cd.practice.app.karate.test.user

import com.intuit.karate.junit5.Karate

internal class UserApiTest {
    @Karate.Test
    fun getUser(): Karate = Karate.run("get-user").relativeTo(this::class.java)

    @Karate.Test
    fun register(): Karate = Karate.run("register-user").relativeTo(this::class.java)

    @Karate.Test
    fun update(): Karate = Karate.run("update-user").relativeTo(this::class.java)

    @Karate.Test
    fun delete(): Karate = Karate.run("delete-user").relativeTo(this::class.java)
}
