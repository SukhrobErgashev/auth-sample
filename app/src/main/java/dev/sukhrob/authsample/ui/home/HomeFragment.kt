package dev.sukhrob.authsample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.sukhrob.authsample.data.remote.response.ResponseState
import dev.sukhrob.authsample.data.remote.api.UserApi
import dev.sukhrob.authsample.data.remote.response.User
import dev.sukhrob.authsample.repository.UserRepository
import dev.sukhrob.authsample.databinding.FragmentHomeBinding
import dev.sukhrob.authsample.ui.base.BaseFragment
import dev.sukhrob.authsample.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.response.user)
                }
                is ResponseState.Loading -> {
                    binding.progressbar.visible(true)
                }
                else -> {}
            }
        }

        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(user: User) {
        with(binding) {
            textViewId.text = user.id.toString()
            textViewName.text = user.name
            textViewEmail.text = user.email
        }
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPref.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }
}