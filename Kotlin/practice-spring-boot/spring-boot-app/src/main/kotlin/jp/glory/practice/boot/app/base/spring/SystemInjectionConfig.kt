package jp.glory.practice.boot.app.base.spring

import org.springframework.beans.factory.BeanRegistrarDsl
import java.time.Clock

object SystemInjectionConfig {
    fun BeanRegistrarDsl.configureSystem() {
        registerBean { Clock.systemDefaultZone() }
    }
}
