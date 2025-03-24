package com.onehungary.one.domain

import javax.inject.Singleton

@Singleton
class AuthenticationStorage {

    private var _isSessionAuthenticated: Boolean? = null
    val isSessionAuthenticated: Boolean?
        get() = _isSessionAuthenticated

    fun saveSession(isAuthenticated: Boolean?) {
        _isSessionAuthenticated = isAuthenticated
    }

}