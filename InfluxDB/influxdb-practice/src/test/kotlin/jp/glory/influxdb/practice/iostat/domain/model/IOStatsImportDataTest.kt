package jp.glory.influxdb.practice.iostat.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

internal class IOStatsImportDataTest {
    @Nested
    inner class CreateEvent {
        @Test
        fun testSingleRows() {
            val importData = listOf(
                "KB/t,tps,MB/s,us,sy,id,1m,5m,15m",
                "32.11,8,0.24,6,2,92,2.66,3.51,3.27"
            )

            val clock = Clock.fixed(
                Instant.parse("2023-02-19T15:00:00Z"),
                ZoneId.of("UTC")
            )
            val baseRecordAt = OffsetDateTime.now(clock)
            val sut = IOStatsImportData(importData)
            val actual = sut.createEvent(clock)

            val results = actual.results
            assertEquals(1, results.count())

            assertStatsResult(
                actual = results[0],
                recordAt = baseRecordAt,
                bytesPerTransfer = 32_880,
                transfersPerSecond = 8,
                bytesPerSecond = 251_658,
                user = 6,
                system = 2,
                idle = 92,
                per1Minutes = 2.66,
                per5Minutes = 3.51,
                per15Minutes = 3.27
            )
        }

        @Test
        fun testMultiResult() {
            val importData = listOf(
                "KB/t,tps,MB/s,us,sy,id,1m,5m,15m",
                "32.11,8,0.24,6,2,92,3.57,3.36,3.35",
                "160.26,213,33.26,6,6,88,3.57,3.36,3.35",
                "10.59,209,2.16,9,7,84,3.57,3.36,3.35"
            )

            val clock = Clock.fixed(
                Instant.parse("2023-02-19T15:00:00Z"),
                ZoneId.of("UTC")
            )
            val baseRecordAt = OffsetDateTime.now(clock)
            val sut = IOStatsImportData(importData)
            val actual = sut.createEvent(clock)

            val results = actual.results
            assertEquals(3, results.count())

            // 32.11,8,0.24,6,2,92,3.57,3.36,3.35
            assertStatsResult(
                actual = results[0],
                recordAt = baseRecordAt,
                bytesPerTransfer = 32_880,
                transfersPerSecond = 8,
                bytesPerSecond = 251_658,
                user = 6,
                system = 2,
                idle = 92,
                per1Minutes = 3.57,
                per5Minutes = 3.36,
                per15Minutes = 3.35
            )

            // 160.26,213,33.26,6,6,88,3.57,3.36,3.35"
            assertStatsResult(
                actual = results[1],
                recordAt = baseRecordAt.plusSeconds(1),
                bytesPerTransfer = 164_106,
                transfersPerSecond = 213,
                bytesPerSecond = 34_875_637,
                user = 6,
                system = 6,
                idle = 88,
                per1Minutes = 3.57,
                per5Minutes = 3.36,
                per15Minutes = 3.35
            )

            // 10.59,209,2.16,9,7,84,3.57,3.36,3.35
            assertStatsResult(
                actual = results[2],
                recordAt = baseRecordAt.plusSeconds(2),
                bytesPerTransfer = 10_844,
                transfersPerSecond = 209,
                bytesPerSecond = 2_264_924,
                user = 9,
                system = 7,
                idle = 84,
                per1Minutes = 3.57,
                per5Minutes =3.36,
                per15Minutes = 3.35
            )
        }
    }

    private fun assertStatsResult(
        actual: IOStatsResult,
        recordAt: OffsetDateTime,
        bytesPerTransfer: Long,
        transfersPerSecond: Int,
        bytesPerSecond: Long,
        user: Int,
        system: Int,
        idle: Int,
        per1Minutes: Double,
        per5Minutes: Double,
        per15Minutes: Double
    ) {
        assertEquals(recordAt, actual.recordAt)

        val diskResult = actual.diskStat
        assertEquals(bytesPerTransfer, diskResult.bytesPerTransfer)
        assertEquals(transfersPerSecond, diskResult.transfersPerSecond)
        assertEquals(bytesPerSecond, diskResult.bytesPerSecond)

        val cpuResult = actual.cpuStat
        assertEquals(user, cpuResult.user)
        assertEquals(system, cpuResult.system)
        assertEquals(idle, cpuResult.idle)

        val loadAverage = cpuResult.loadAverage
        assertEquals(per1Minutes, loadAverage.per1Minutes)
        assertEquals(per5Minutes, loadAverage.per5Minutes)
        assertEquals(per15Minutes, loadAverage.per15Minutes)
    }
}