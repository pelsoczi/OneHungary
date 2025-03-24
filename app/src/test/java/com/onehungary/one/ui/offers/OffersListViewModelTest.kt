package com.onehungary.one.ui.offers

import app.cash.turbine.test
import com.onehungary.one.domain.model.OffersEntity
import com.onehungary.one.domain.usecase.FetchOffersListUseCase
import io.mockk.coEvery
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


class OffersListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val offersListUseCase = mockk<FetchOffersListUseCase>()

    lateinit var viewModel: OffersListViewModel

    @Before
    fun setUp() {
        viewModel = OffersListViewModel(
            fetchOffersListUseCase = offersListUseCase,
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
                assert(it is OffersListViewState.Empty)
            }
        }
    }

    @Test
    fun `refresh and domain usecase returns offers`() = runTest {
        // given
        val list = listOf(mockk<OffersEntity>())
        coEvery { offersListUseCase.invoke() } returns flowOf(list)
        // when
        viewModel.viewState.test {
            viewModel.handle(OffersListViewAction.Refresh)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is OffersListViewState.Loading)
            }
            awaitItem().let {
                assert(it is OffersListViewState.Offers)
            }
        }
    }

    @Test
    fun `refresh and domain usecase doesnt return offers`() = runTest {
        // given
        val list = emptyList<OffersEntity>()
        coEvery { offersListUseCase.invoke() } returns flowOf(list)
        // when
        viewModel.viewState.test {
            viewModel.handle(OffersListViewAction.Refresh)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is OffersListViewState.Loading)
            }
            awaitItem().let {
                assert(it is OffersListViewState.TryAgainLater)
            }
        }
    }

    @Test
    fun `refresh and domain usecase throws network exception`() = runTest {
        // given
        coEvery { offersListUseCase.invoke() } throws RuntimeException()
        // when
        viewModel.viewState.test {
            viewModel.handle(OffersListViewAction.Refresh)
            // then
            skipItems(1)
            awaitItem().let {
                assert(it is OffersListViewState.Loading)
            }
            awaitItem().let {
                assert(it is OffersListViewState.NetworkError)
            }
        }
    }

    @Test
    fun `usecase returns list and viewmodel partitions offers sections`() {
        // given
        val one = OffersEntity("1", 2, true, "One time 1 GB", "Let's choose between our data packages. This is a special offer just for you!")
        val two = OffersEntity("2", 1, true, "One time 300 MB", "Let's choose between our data packages.")
        val four = OffersEntity("4", 3, true, "One time 100 MB", "")
        val five = OffersEntity("5", null, true, "One time 200 MB", "Let's choose between our data packages.")
        val three = OffersEntity("3", 4, false, "One time 500 MB", "Let's choose between our data packages.")
        // known sort order
        val specialAndNonSpecialList = listOf(one, two, four, five, three)

        // when
        var state = viewModel.partitionOffersList(specialAndNonSpecialList)
        // then
        assert(state is OffersListViewState.Offers)
        state as OffersListViewState.Offers
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>().size == 2)
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>()[0].type == OfferListItemTitles.SPECIAL)
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>()[1].type == OfferListItemTitles.OFFERS)

        // and given
        val specialList = listOf(one, two, four, five)
        // and when
        state = viewModel.partitionOffersList(specialList)
        // and then
        assert(state is OffersListViewState.Offers)
        state as OffersListViewState.Offers
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>().size == 1)
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>()[0].type == OfferListItemTitles.SPECIAL)

        // and given
        val offersList = listOf(three)
        // and when
        state = viewModel.partitionOffersList(offersList)
        // and then
        assert(state is OffersListViewState.Offers)
        state as OffersListViewState.Offers
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>().size == 1)
        assert(state.entities.filterIsInstance<OfferListItem.TitleListType>()[0].type == OfferListItemTitles.OFFERS)

        // and given
        val emptyList = emptyList<OffersEntity>()
        // and when
        state = viewModel.partitionOffersList(emptyList)
        // and then
        assert(state is OffersListViewState.TryAgainLater)
    }

}