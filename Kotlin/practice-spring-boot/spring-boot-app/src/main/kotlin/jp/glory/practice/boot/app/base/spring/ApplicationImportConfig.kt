package jp.glory.practice.boot.app.base.spring

import jp.glory.practice.boot.app.auth.AuthBeanRegister.configureAuth
import jp.glory.practice.boot.app.base.spring.SystemInjectionConfig.configureSystem
import jp.glory.practice.boot.app.user.UserBeanRegister.configureUser
import org.springframework.beans.factory.BeanRegistrarDsl

class ApplicationImportConfig : BeanRegistrarDsl({
    configureSystem()
    configureAuth()
    configureUser()
})
