package jp.glory.practice.fullstack.server.base.adaptor.store


import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object DaoModule {
    fun createModule(): Module = module {
        singleOf(::AuthDao)
        singleOf(::ReviewDao)
        singleOf(::TokenDao)
        singleOf(::UserDao)
    }
}