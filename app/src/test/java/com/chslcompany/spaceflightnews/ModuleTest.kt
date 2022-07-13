package com.chslcompany.spaceflightnews

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import com.chslcompany.spaceflightnews.data.repository.PostRepositoryImpl
import com.chslcompany.spaceflightnews.domain.usecase.GetLatestPostsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureDomainModulesForTest() = module {
    factory { GetLatestPostsUseCase(get()) }
}

fun configureDataModulesForTest(baseUrl: String) = module {
    single {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(T::class.java)
    }

    single<PostRepository> { PostRepositoryImpl(get()) }
}