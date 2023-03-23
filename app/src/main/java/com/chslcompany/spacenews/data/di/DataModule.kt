package com.chslcompany.spacenews.data.di

import com.chslcompany.spacenews.data.db.PostDatabase
import com.chslcompany.spacenews.data.repository.PostRepository
import com.chslcompany.spacenews.data.repository.PostRepositoryImpl
import com.chslcompany.spacenews.data.service.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object DataModule {

    private const val URL_BASE = "https://api.spaceflightnewsapi.net/v3/"

    fun load() = loadKoinModules(networkModule() + repositoryModule() + daoModule())

    private fun daoModule() = module {
        single { PostDatabase.getInstance(androidContext()).dao }
    }

    private fun repositoryModule() = module {
        single<PostRepository> { PostRepositoryImpl(get(),get()) }
    }

    private fun networkModule(): Module {
        return module {
            single<SpaceFlightNewsService> { providerService(get(), get()) }
            single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
            single {
                OkHttpClient.Builder().apply {
                    connectTimeout(60L, TimeUnit.SECONDS)
                    readTimeout(60L, TimeUnit.SECONDS)
                    writeTimeout(60L, TimeUnit.SECONDS)
                        .addInterceptor(
                            HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                }.build()
            }
        }
    }

    private inline fun <reified T> providerService(client: OkHttpClient, factory: Moshi): T =
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
            .create(T::class.java)


}

