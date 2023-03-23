package com.chslcompany.spacenews.domain.di

import com.chslcompany.spacenews.domain.usecase.GetLatestPostsUseCase
import com.chslcompany.spacenews.domain.usecase.GetPostTitleContainsUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() = loadKoinModules(useCaseModule())

    private fun useCaseModule(): Module =
        module {
            factory { GetLatestPostsUseCase(get()) }
            factory { GetPostTitleContainsUseCase(get()) }
        }

}