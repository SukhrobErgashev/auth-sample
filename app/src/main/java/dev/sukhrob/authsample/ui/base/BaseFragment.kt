package dev.sukhrob.authsample.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dev.sukhrob.authsample.data.local.UserPreferences
import dev.sukhrob.authsample.data.remote.RemoteDataSource
import dev.sukhrob.authsample.data.remote.api.UserApi
import dev.sukhrob.authsample.repository.BaseRepository
import dev.sukhrob.authsample.startNewActivity
import dev.sukhrob.authsample.ui.auth.AuthActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()
    protected lateinit var userPref: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPref = UserPreferences(requireContext())
        lifecycleScope.launch { userPref.authToken.first() }

        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        binding = getFragmentBinding(inflater, container)
        return binding.root
    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPref.authToken.first()
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
        userPref.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}