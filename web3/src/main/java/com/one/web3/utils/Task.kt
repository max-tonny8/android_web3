package com.one.web3.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlin.math.max

interface Task<Param, Result> {

    fun priority(): Int = 0

    suspend fun execute(param: Param): ResultState<Result> {

        return runCatching {

            ResultState.Success(executeTask(param))
        }.getOrElse {

            ResultState.Failed("", it)
        }
    }

    suspend fun executeTask(param: Param): Result
}


suspend fun <Param, Result> List<Task<Param, Result>>.executeSyncByPriority(param: Param): ResultState<Result> = channelFlow {


    val outputs = sortedByDescending {

        it.priority()
    }.map {

        val state = it.execute(param)

        if (state.isSuccess()) {

            offerActiveAwait(state)
            return@channelFlow
        }

        state
    }


    if (outputs.all { it.isFailed() }) {

        offerActive(outputs.first())
    }


    awaitClose {
    }
}.first()


suspend fun <Param, Result> List<Task<Param, Result>>.executeAsyncByPriority(param: Param): ResultState<Result> = channelFlow {

    if (isEmpty()) {

        offerActiveAwait(ResultState.Failed("", RuntimeException("task empty")))
        return@channelFlow
    }

    val listDeferred = sortedByDescending {

        it.priority()
    }.map {

        async { it.execute(param) }
    }


    val job = launch {

        val outputs = arrayListOf<ResultState<Result>>()

        for (deferred in listDeferred) deferred.await().let {

            if (it.isSuccess()) {

                offerActive(it)
                return@launch
            }

            outputs.add(it)
        }

        if (outputs.all { it.isFailed() }) {

            offerActive(outputs.first())
        }
    }


    awaitClose {

        job.cancel()

        listDeferred.map { it.cancel() }
    }
}.first()


suspend fun <Param, Result> List<Task<Param, Result>>.executeAsyncByFast(param: Param): ResultState<Result> = channelFlow {

    if (isEmpty()) {

        offerActiveAwait(ResultState.Failed("", RuntimeException("task empty")))
        return@channelFlow
    }

    val listDeferred: Collection<Deferred<ResultState<Result>>> = sortedByDescending {

        it.priority()
    }.map {

        async {

            val output = it.execute(param)

            if (output.isSuccess()) {

                offerActive(output)
            }

            output
        }
    }

    val job = launch {

        val listTranslate = listDeferred.awaitAll()

        if (listTranslate.all { it.isFailed() }) {

            offerActive(listTranslate.first())
        }
    }

    awaitClose {

        job.cancel()

        listDeferred.map { it.cancel() }
    }
}.first()

suspend fun <Param, Result> List<Task<Param, Result>>.executeAsyncAll(param: Param) = channelFlow {

    if (isEmpty()) {

        offerActiveAwait(ResultState.Failed("", RuntimeException("task empty")))
        return@channelFlow
    }

    val listDeferred = sortedByDescending {

        it.priority()
    }.map {

        async {

            it.execute(param)
        }
    }

    val job = launch {

        offerActive(listDeferred.awaitAll())
    }

    awaitClose {

        job.cancel()

        listDeferred.map { it.cancel() }
    }
}

suspend fun <Param, Result> List<Task<Param, Result>>.executeAsync(param: Param): Flow<ResultState<Result>> = channelFlow {

    if (isEmpty()) {

        offerActiveAwait(ResultState.Failed("", RuntimeException("task empty")))
        return@channelFlow
    }

    val listDeferred = sortedByDescending {

        it.priority()
    }.map {

        async {

            val output = it.execute(param)

            if (output.isSuccess()) {

                offerActive(output)
            }

            output
        }
    }

    val job = launch {

        val listTranslate = listDeferred.awaitAll()

        if (listTranslate.all { it.isFailed() }) {

            offerActive(listTranslate.first())
        }
    }

    awaitClose {

        job.cancel()

        listDeferred.map { it.cancel() }
    }
}


suspend fun <T> ProducerScope<T>.offerActiveAwait(t: T, start: Long = -1, max: Long = -1) {

    if (start > 0 && max > 0) {
        delay(max(0, max - System.currentTimeMillis() + start))
    }

    offerActive(t)

    awaitClose { }
}

fun <T> ProducerScope<T>.offerActive(t: T) {

    if (isActive) trySend(t)
}
