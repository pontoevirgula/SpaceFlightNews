package com.chslcompany.spaceflightnews.core

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<Source> {

    abstract suspend fun execute() : Flow<Source>
}