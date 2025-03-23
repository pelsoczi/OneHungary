package com.onehungary.one.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.onehungary.one.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButtonLogin.setOnClickListener {
            viewModel.handle(LoginViewAction.LoginUser)
        }
        binding.loginButtonGuest.setOnClickListener {
            viewModel.handle(LoginViewAction.LoginGuest)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    when (it) {
                        is LoginViewState.Authenticated -> navigateToOffers(it)
                        is LoginViewState.LoginScreen -> {}
                    }
                }
            }
        }
    }

    private fun navigateToOffers(state: LoginViewState.Authenticated) {
        findNavController().navigate(
            LoginFragmentDirections.onLoginClick()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}