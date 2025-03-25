package com.onehungary.one.ui.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.onehungary.one.domain.model.DetailsEntity
import com.onehungary.one.domain.usecase.FetchDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
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

    private val fetchDetailsUseCase = mockk<FetchDetailsUseCase>()

    lateinit var viewModel: OfferDetailsViewModel

    @Before
    fun setUp() {
        viewModel = OfferDetailsViewModel(
            fetchDetailsUseCase = fetchDetailsUseCase,
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

    @Test
    fun `refresh and domain usecase returns offers`() = runTest {
        // given
        val detail = mockk<DetailsEntity>()
        val detailId = savedState.get<Int>("id") ?: 1
        coEvery { fetchDetailsUseCase.invoke(any()) } returns flowOf(detail)
        // when
        viewModel.viewState.test {
            viewModel.handle(OfferDetailsViewAction.RetrieveOfferDetails)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is OfferDetailsViewState.OfferDetail)
            }
        }
        coVerify(exactly = 1) { fetchDetailsUseCase.invoke(detailId) }
    }

    @Test
    fun `refresh and domain usecase returns runtime exception`() = runTest {
        // given
        val detailId = savedState.get<Int>("id") ?: 1
        coEvery { fetchDetailsUseCase.invoke(any()) } throws RuntimeException()
        // when
        viewModel.viewState.test {
            viewModel.handle(OfferDetailsViewAction.RetrieveOfferDetails)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is OfferDetailsViewState.TryAgainLater)
            }
        }
        coVerify { fetchDetailsUseCase.invoke(detailId) }
    }

    @Test
    fun `refresh and domain usecase returns network exception`() = runTest {
        // given
        val detailId = savedState.get<Int>("id") ?: 1
        coEvery { fetchDetailsUseCase.invoke(any()) } throws Exception()
        // when
        viewModel.viewState.test {
            viewModel.handle(OfferDetailsViewAction.RetrieveOfferDetails)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is OfferDetailsViewState.NetworkError)
            }
        }
        coVerify { fetchDetailsUseCase.invoke(detailId) }
    }

}