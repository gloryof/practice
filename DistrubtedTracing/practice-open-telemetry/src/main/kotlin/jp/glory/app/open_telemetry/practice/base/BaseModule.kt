package jp.glory.app.open_telemetry.practice.base

import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseTelemetry
import org.koin.core.module.Module
import org.koin.dsl.module

object BaseModule {
    fun baseModule(): Module = module {
        single { UseCaseTelemetry() }
    }
}