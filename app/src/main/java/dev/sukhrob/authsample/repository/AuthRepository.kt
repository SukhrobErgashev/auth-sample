package dev.sukhrob.authsample.repository

import dev.sukhrob.authsample.data.local.UserPreferences
import dev.sukhrob.authsample.data.remote.api.AuthApi

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}