package jp.glory.practice.boot.app.auth

import jp.glory.practice.boot.app.auth.data.AuthDao
import org.springframework.beans.factory.BeanRegistrarDsl

class AuthBeanRegister : BeanRegistrarDsl({
    dao()
})

private fun BeanRegistrarDsl.dao() {
    registerBean<AuthDao>()
}
