package com.one.web3.utils

sealed class ResultState<out T> {

    object Start : ResultState<Nothing>()

    data class Success<T>(val data: T) : ResultState<T>()

    data class Failed(val message: String, val cause: Throwable) : ResultState<Nothing>()
}

fun <T> ResultState<T>.toStart() = this as? ResultState.Start

fun <T> ResultState<T>.toFailed() = this as? ResultState.Failed

fun <T> ResultState<T>.toSuccess() = this as? ResultState.Success


inline fun <T> ResultState<T>.isStart() = this is ResultState.Start

inline fun <T> ResultState<T>.isFailed() = this is ResultState.Failed

inline fun <T> ResultState<T>.isSuccess() = this is ResultState.Success


inline fun <T> ResultState<T>.doStart(action: () -> Unit) = toStart()?.let {
    action.invoke()
}

inline fun <T> ResultState<T>.doSuccess(action: (T) -> Unit) = toSuccess()?.data?.let {
    action.invoke(it)
}

inline fun <T> ResultState<T>.doFailed(action: (message: String, cause: Throwable) -> Unit) = toFailed()?.let {
    action.invoke(it.message, it.cause)
}