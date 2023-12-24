package jp.glory.practice.grpc.base.adaptor.web

import java.time.LocalDate

object GrpcRequestUtil {
    fun toLocalDate(value: String): LocalDate =
        LocalDate.parse(value)
}