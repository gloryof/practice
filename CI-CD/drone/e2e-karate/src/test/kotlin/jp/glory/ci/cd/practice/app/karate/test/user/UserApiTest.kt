package jp.glory.ci.cd.practice.app.karate.test.user

import com.intuit.karate.junit5.Karate

internal class UserApiTest {

    @Karate.Test
    fun getUser(): Karate = Karate.run("get-user").relativeTo(this::class.java)
}
