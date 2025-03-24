package com.onehungary.one.ui.login

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.onehungary.one.domain.usecase.LoginUserUseCase
import com.onehungary.one.domain.usecase.RemoveLoginUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val loginUserUseCase = mockk<LoginUserUseCase>()
    private val removeLoginUseCase = mockk<RemoveLoginUseCase>()

    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            loginUserUseCase = loginUserUseCase,
            removeLoginUseCase = removeLoginUseCase,
            dispatcher = testDispatcher,
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewmodel initial state`() = runTest {
        // when
        viewModel.viewState.test {
            // then
            awaitItem().let {
                assert(it is LoginViewState.LoginScreen)
            }
        }
    }

    @Test
    fun `viewmodel login user action`() = runTest {
        // given
        val action = LoginViewAction.LoginUser
        // when
        viewModel.viewState.test {
            viewModel.handle(action)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is LoginViewState.Authenticated)
                it as LoginViewState.Authenticated
                Truth.assertThat(it.hasSession).isTrue()
            }
        }
        coVerify(exactly = 1) { loginUserUseCase.invoke(true) }
    }

    @Test
    fun `viewmodel login guest action`() = runTest {
        // given
        val action = LoginViewAction.LoginGuest
        // when
        viewModel.viewState.test {
            viewModel.handle(action)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is LoginViewState.Authenticated)
                it as LoginViewState.Authenticated
                Truth.assertThat(it.hasSession).isFalse()
            }
        }
        coVerify(exactly = 1) { loginUserUseCase.invoke(false) }
    }

}