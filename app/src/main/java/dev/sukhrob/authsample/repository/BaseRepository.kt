package dev.sukhrob.authsample.repository

import dev.sukhrob.authsample.data.remote.response.ResponseState
import dev.sukhrob.authsample.data.remote.api.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ResponseState<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResponseState.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ResponseState.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        ResponseState.Failure(true, null, null)
                    }
                }
            }
        }
    }

    suspend fun logout(api: UserApi) = safeApiCall {
        api.logout()
    }
}