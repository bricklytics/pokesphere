package com.bricklytics.pokesphere.domainlayer.base.error.wrapper

sealed class ResultWrapper<out T : Any, out E : Any> {

    data class Success<out T : Any>(val data: T) : ResultWrapper<T, Nothing>() {
        init {
            requireNotNull(data) { "Success data cannot be null" }
        }
    }

    data class Error<out E : Any>(val error: E) : ResultWrapper<Nothing, E>() {
        init {
            requireNotNull(error) { "Error data cannot be null" }
        }
    }

    inline fun <R : Any> transformError(transform: (E) -> R): ResultWrapper<T, R> =
        when (this) {
            is Error -> Error(transform(error))
            is Success -> this
        }

    inline fun <R : Any> transformSuccess(transform: (T) -> R): ResultWrapper<R, E> =
        when (this) {
            is Success -> Success(transform(data))
            is Error -> this
        }

    inline fun onSuccess(action: (T) -> Unit): ResultWrapper<T, E> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (E) -> Unit): ResultWrapper<T, E> {
        if (this is Error) action(error)
        return this
    }

    fun isSuccess() = this is Success
    fun success(): T? = if (this is Success) data else null
    fun error(): E? = if (this is Error) error else null
}