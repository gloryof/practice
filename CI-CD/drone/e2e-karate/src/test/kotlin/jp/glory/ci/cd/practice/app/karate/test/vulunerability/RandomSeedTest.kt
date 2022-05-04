package jp.glory.ci.cd.practice.app.karate.test.vulunerability

import com.intuit.karate.junit5.Karate

internal class RandomSeedTest {

    @Karate.Test
    fun returnNumber(): Karate = Karate.run("random-seed").relativeTo(this::class.java)
}
