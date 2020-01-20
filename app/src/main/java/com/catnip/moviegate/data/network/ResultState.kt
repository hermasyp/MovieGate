package com.catnip.moviegate.data.network

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
sealed class ResultState<T> {
    data class Progress<T>(var loading: Boolean) : ResultState<T>()
    data class Success<T>(var data: T) : ResultState<T>()
    data class Failure<T>(val e: Throwable) : ResultState<T>()
    companion object {
        fun <T> loading(isLoading: Boolean): ResultState<T> = Progress(isLoading)

        fun <T> success(data: T): ResultState<T> = Success(data)

        fun <T> failure(e: Throwable): ResultState<T> = Failure(e)
    }
}