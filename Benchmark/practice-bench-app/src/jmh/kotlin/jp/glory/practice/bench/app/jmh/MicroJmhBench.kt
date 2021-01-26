package jp.glory.practice.bench.app.jmh

import org.openjdk.jmh.annotations.Benchmark
import java.math.BigDecimal

open class MicroJmhBench {
    @Benchmark
    fun test() {}
}
