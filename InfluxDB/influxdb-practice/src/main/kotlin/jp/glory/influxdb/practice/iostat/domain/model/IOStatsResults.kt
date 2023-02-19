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
    val user: Int,
    val system: Int,
    val idle: Int,
    val loadAverage: LoadAverage
)

class LoadAverage(
    val per1Minutes: Double,
    val per5Minutes: Double,
    val per15Minutes: Double
)

class DiskStat(
    val bytesPerTransfer: Long,
    val transfersPerSecond: Int,
    val bytesPerSecond: Long
)