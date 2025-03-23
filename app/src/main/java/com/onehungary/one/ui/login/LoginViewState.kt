package com.onehungary.one.ui.login

sealed class LoginViewState {

    object LoginScreen : LoginViewState()

    data class Authenticated(
        val hasSession: Boolean
    ) : LoginViewState()

}