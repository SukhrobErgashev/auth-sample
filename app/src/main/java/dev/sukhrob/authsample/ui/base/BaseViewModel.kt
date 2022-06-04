package dev.sukhrob.authsample.ui.base

import androidx.lifecycle.ViewModel
import dev.sukhrob.authsample.data.remote.api.UserApi
import dev.sukhrob.authsample.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
): ViewModel() {

    suspend fun logout(api: UserApi) = repository.logout(api)

}