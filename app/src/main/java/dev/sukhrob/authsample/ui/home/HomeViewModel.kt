package dev.sukhrob.authsample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.sukhrob.authsample.data.remote.response.ResponseState
import dev.sukhrob.authsample.data.remote.response.LoginResponse
import dev.sukhrob.authsample.repository.UserRepository
import dev.sukhrob.authsample.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user = MutableLiveData<ResponseState<LoginResponse>>()
    val user: LiveData<ResponseState<LoginResponse>>
        get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = ResponseState.Loading
        _user.value = repository.getUser()
    }

}