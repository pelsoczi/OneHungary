package com.onehungary.one.domain.usecase

import com.onehungary.one.domain.AuthenticationStorage
import javax.inject.Inject

class RemoveLoginUseCase @Inject constructor(
    private val authenticationStorage: AuthenticationStorage
) {
    operator fun invoke() {
        authenticationStorage.saveSession(null)
    }
}