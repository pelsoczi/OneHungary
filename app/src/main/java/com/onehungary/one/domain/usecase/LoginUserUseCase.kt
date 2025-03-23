package com.onehungary.one.domain.usecase

import com.onehungary.one.domain.AuthenticationStorage
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authenticationStorage: AuthenticationStorage
) {

    suspend operator fun invoke(hasSession: Boolean) {
        authenticationStorage.saveSession(hasSession)
    }

}
