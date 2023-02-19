package jp.glory.influxdb.practice.iostat.domain.model

import jp.glory.influxdb.practice.iostat.domain.model.*
import java.time.Clock
import java.time.OffsetDateTime

class IOStatsImportData(
    private val rows: List<String>
) {
    fun createEvent(clock: Clock): IOStatsResults {
        val resultRows = rows.subList(1, rows.size)
        val baseRecordAt = OffsetDateTime.now(clock)

        return resultRows
            .mapIndexed { index, row ->
                parseRow(
                    recordAt = baseRecordAt.plusSeconds(index.toLong()),
                    row = row
                )
            }
            .let { IOStatsResults(it) }
    }

    private fun parseRow(recordAt: OffsetDateTime, row: String): IOStatsResult {
        val values = row.split(",")
        return IOStatsResult(
            recordAt = recordAt,
            diskStat = DiskStat(
                bytesPerTransfer = (values[0].toDouble() * 1024).toLong(),
                transfersPerSecond = values[1].toInt(),
                bytesPerSecond = (values[2].toDouble() * 1024 * 1024).toLong()
            ),
            cpuStat = CPUStat(
                user = values[3].toInt(),
                system = values[4].toInt(),
                idle = values[5].toInt(),
                loadAverage = LoadAverage(
                    per1Minutes = values[6].toDouble(),
                    per5Minutes = values[7].toDouble(),
                    per15Minutes = values[8].toDouble()
                )
            )
        )
    }
}