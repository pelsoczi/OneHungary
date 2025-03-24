package com.onehungary.one.ui.login

sealed class LoginViewAction {

    object LoginUser : LoginViewAction()

    object LoginGuest : LoginViewAction()

    object RemoveAuthentication : LoginViewAction()

}