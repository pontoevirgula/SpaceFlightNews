package com.chslcompany.spaceflightnews

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

private const val URL_BASE_TEST = "https://api.spaceflightnewsapi.net/v3/"

fun setupTestAppComponent() = startKoin {
    loadKoinModules(
        configureDataModulesForTest(URL_BASE_TEST)
                + configureDomainModulesForTest()
    )
}