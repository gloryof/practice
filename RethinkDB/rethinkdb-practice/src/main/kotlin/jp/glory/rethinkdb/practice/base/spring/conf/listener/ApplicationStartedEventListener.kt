package jp.glory.rethinkdb.practice.base.spring.conf.listener

import jp.glory.rethinkdb.practice.infra.store.dao.NotificationEventRegister
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener

class ApplicationStartedEventListener : ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        event.applicationContext.getBean(NotificationEventRegister::class.java)
            .let { it.register() }
    }
}