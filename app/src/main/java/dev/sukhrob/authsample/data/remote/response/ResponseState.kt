package dev.sukhrob.authsample.data.remote.response

import okhttp3.ResponseBody

sealed class ResponseState<out T> {
    data class Success<out T>(val response: T) : ResponseState<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ): ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
}