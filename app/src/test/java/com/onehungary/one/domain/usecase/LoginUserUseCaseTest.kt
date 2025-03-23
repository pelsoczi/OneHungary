package com.onehungary.one.domain.usecase

import com.onehungary.one.domain.AuthenticationStorage
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginUserUseCaseTest {

    private val authenticationStorage = mockk<AuthenticationStorage>()

    private lateinit var useCase: LoginUserUseCase

    @Before
    fun setUp() {
        useCase = LoginUserUseCase(
            authenticationStorage = authenticationStorage
        )
    }

    @Test
    fun `invocation of hasSession`() = runTest {
        // given
        val hasSession = true
        // when
        useCase.invoke(hasSession)
        // then
        coVerify(exactly = 1) { authenticationStorage.saveSession(true) }

        // and when
        useCase.invoke(!hasSession)
        // and then
        coVerify(exactly = 1) { authenticationStorage.saveSession(false) }
    }

}