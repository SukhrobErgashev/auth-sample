package dev.sukhrob.authsample.repository

import dev.sukhrob.authsample.data.remote.api.UserApi

class UserRepository(
    private val api: UserApi
) : BaseRepository() {

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

}