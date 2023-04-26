package com.example.criticaltechworkschallenge.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


@OptIn(ExperimentalCoroutinesApi::class)
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {

    val data = query().first()

    emit(Resource.Loading(data))

    val fetchFlow = flow {
        try {
            val fetchedData = fetch()
            saveFetchResult(fetchedData)
            emit(Resource.Success(query().first()))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable, data))
        }
    }

    if (shouldFetch(data)) {
        emitAll(fetchFlow.flatMapLatest { query().map { Resource.Success(it) } })
    } else {
        emitAll(query().map { Resource.Success(it) })
    }
}