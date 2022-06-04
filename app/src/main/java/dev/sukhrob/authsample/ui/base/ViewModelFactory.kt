package dev.sukhrob.authsample.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.sukhrob.authsample.repository.AuthRepository
import dev.sukhrob.authsample.repository.BaseRepository
import dev.sukhrob.authsample.repository.UserRepository
import dev.sukhrob.authsample.ui.auth.AuthViewModel
import dev.sukhrob.authsample.ui.home.HomeViewModel

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModel class Not Found")
        }
    }

}