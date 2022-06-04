package dev.sukhrob.authsample.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.sukhrob.authsample.data.remote.response.ResponseState
import dev.sukhrob.authsample.data.remote.response.LoginResponse
import dev.sukhrob.authsample.repository.AuthRepository
import dev.sukhrob.authsample.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<ResponseState<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<ResponseState<LoginResponse>>
        get() = _loginResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginResponse.value = ResponseState.Loading
        _loginResponse.value = repository.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }

}