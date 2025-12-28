package jp.glory.practice.boot.app.base.spring

import org.springframework.beans.factory.BeanRegistrarDsl
import java.time.Clock

class SystemInjectionConfig : BeanRegistrarDsl({
    registerBean { Clock.systemDefaultZone() }
})
