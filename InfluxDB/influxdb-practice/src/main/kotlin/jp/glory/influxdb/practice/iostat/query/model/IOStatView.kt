package jp.glory.influxdb.practice.iostat.query.model

import java.time.OffsetDateTime

class IOStatView(
    val cpuView: CPUView,
    val diskView: DiskView
)

class CPUView(
    val user: ViewRows<Long>,
    val system: ViewRows<Long>,
    val idle: ViewRows<Long>,
    val per1Minutes: ViewRows<Double>,
    val per5Minutes: ViewRows<Double>,
    val per15Minutes: ViewRows<Double>
)

class DiskView(
    val bytesPerTransfer: ViewRows<Long>,
    val transfersPerSecond: ViewRows<Long>,
    val bytesPerSecond: ViewRows<Long>
)

class ViewRows<T>(
    val viewRows: List<ViewRow<T>>
)

class ViewRow<T>(
    val recordAt: OffsetDateTime,
    val value: T
)