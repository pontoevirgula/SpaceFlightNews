package com.chslcompany.spaceflightnews

import android.app.Application
import com.chslcompany.spaceflightnews.data.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InitApp: Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InitApp)
        }

        PresentationModule.load()
    }

}