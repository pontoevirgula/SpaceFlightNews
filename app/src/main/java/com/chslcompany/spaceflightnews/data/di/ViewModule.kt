package com.chslcompany.spaceflightnews.data.di

import com.chslcompany.spaceflightnews.ui.home.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule() : Module {
        return module {
            factory { HomeViewModel() }
        }
    }


}