package jp.glory.practice.boot.app.auth

import jp.glory.practice.boot.app.auth.data.AuthDao
import org.springframework.beans.factory.BeanRegistrarDsl

object AuthBeanRegister {
    fun BeanRegistrarDsl.configureAuth() {
        dao()
    }

    private fun BeanRegistrarDsl.dao() {
        registerBean<AuthDao>()
    }
}
