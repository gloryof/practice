package jp.glory.influxdb.practice.iostat.domain.model

import java.time.OffsetDateTime

class IOStatsResults(
    val results: List<IOStatsResult>
)

class IOStatsResult(
    val recordAt: OffsetDateTime,
    val cpuStat: CPUStat,
    val diskStat: DiskStat
)

class CPUStat(
    val user: Long,
    val system: Long,
    val idle: Long,
    val loadAverage: LoadAverage
)

class LoadAverage(
    val per1Minutes: Double,
    val per5Minutes: Double,
    val per15Minutes: Double
)

class DiskStat(
    val bytesPerTransfer: Long,
    val transfersPerSecond: Long,
    val bytesPerSecond: Long
)
