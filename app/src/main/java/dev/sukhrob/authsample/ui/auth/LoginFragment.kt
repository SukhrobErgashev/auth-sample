package dev.sukhrob.authsample.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dev.sukhrob.authsample.data.remote.api.AuthApi
import dev.sukhrob.authsample.data.remote.response.ResponseState
import dev.sukhrob.authsample.repository.AuthRepository
import dev.sukhrob.authsample.databinding.FragmentLoginBinding
import dev.sukhrob.authsample.enable
import dev.sukhrob.authsample.handleApiError
import dev.sukhrob.authsample.startNewActivity
import dev.sukhrob.authsample.ui.base.BaseFragment
import dev.sukhrob.authsample.ui.home.HomeActivity
import dev.sukhrob.authsample.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        binding.btnLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is ResponseState.Loading)
            when (it) {
                is ResponseState.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.response.user.access_token!!)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is ResponseState.Failure -> handleApiError(it) { login() }
                else -> {}
            }
        })

        // Text change listener
        binding.loginPasswordEdittext.addTextChangedListener {
            val email = binding.loginEmailEdittext.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.loginEmailEdittext.text.toString().trim()
        val password = binding.loginPasswordEdittext.text.toString().trim()

        viewModel.login(email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPref)


}