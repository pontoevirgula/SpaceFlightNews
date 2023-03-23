package com.chslcompany.spacenews

import android.app.Application
import com.chslcompany.spacenews.data.di.DataModule
import com.chslcompany.spacenews.domain.di.DomainModule
import com.chslcompany.spacenews.ui.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InitApp: Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InitApp)
        }

        ViewModelModule.load()
        DataModule.load()
        DomainModule.load()
    }

}