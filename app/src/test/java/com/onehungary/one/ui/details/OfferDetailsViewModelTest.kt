package com.onehungary.one.ui.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class OfferDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val savedState = SavedStateHandle(mapOf("id" to 1))

    lateinit var viewModel: OfferDetailsViewModel

    @Before
    fun setUp() {
        viewModel = OfferDetailsViewModel(
            savedState = savedState,
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
                assert(it is OfferDetailsViewState.Empty)
            }
        }
    }

}