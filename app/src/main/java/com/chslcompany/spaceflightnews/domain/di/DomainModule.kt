package com.chslcompany.spaceflightnews.domain.di

import com.chslcompany.spaceflightnews.domain.usecase.GetLatestPostsUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() = loadKoinModules(useCaseModule())

    private fun useCaseModule(): Module =
        module {
            factory { GetLatestPostsUseCase(get()) }
        }

}