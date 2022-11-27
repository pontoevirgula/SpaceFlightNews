package com.chslcompany.spaceflightnews.core

import kotlinx.coroutines.flow.Flow
import java.lang.UnsupportedOperationException

abstract class BaseUseCase<Param, Source> {

    abstract suspend fun execute(param: Param): Flow<Source>

    suspend operator fun invoke(param: Param) = execute(param)

    abstract class NoParam<Source> : BaseUseCase<Nothing, Source>() {

        abstract fun execute(): Flow<Source>

        final override suspend fun execute(param: Nothing): Flow<Source> {
            throw UnsupportedOperationException()
        }

        operator fun invoke() = execute()
    }
}