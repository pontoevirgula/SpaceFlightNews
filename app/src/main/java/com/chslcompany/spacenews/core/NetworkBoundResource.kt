package com.chslcompany.spacenews.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun  <ResultType, RequestType> networkBoundResources(
    crossinline query : () -> Flow<ResultType>,
    crossinline service : suspend () -> RequestType,
    crossinline saveFetchResult : suspend (RequestType) -> Unit,
    crossinline onError : (Throwable) -> Throwable
) : Flow<Resource<ResultType>> =
    flow {
        var dbData = query().first()
        try {
            saveFetchResult(service())
            dbData = query().first()
            emit(Resource.Success(dbData))
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    data = dbData,
                    error = onError(e)
                )
            )
        }
    }

