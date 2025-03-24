package com.onehungary.one.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onehungary.one.domain.usecase.LoginUserUseCase
import com.onehungary.one.domain.usecase.RemoveLoginUseCase
import com.onehungary.one.ui.login.LoginViewState.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val removeLoginUseCase: RemoveLoginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _viewState = MutableStateFlow<LoginViewState>(LoginScreen)
    val viewState: StateFlow<LoginViewState> = _viewState.asStateFlow()

    fun handle(action: LoginViewAction) = when (action) {
        is LoginViewAction.LoginUser -> loginUser(true)
        is LoginViewAction.LoginGuest -> loginUser(false)
        is LoginViewAction.RemoveAuthentication -> removeAuthentication()
    }

    private fun loginUser(hasSession: Boolean) = viewModelScope.launch(dispatcher) {
        loginUserUseCase.invoke(hasSession)
        _viewState.emit(LoginViewState.Authenticated(hasSession))
    }

    private fun removeAuthentication() = viewModelScope.launch {
        removeLoginUseCase.invoke()
    }

}