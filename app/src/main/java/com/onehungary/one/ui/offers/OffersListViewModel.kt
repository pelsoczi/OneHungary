package com.onehungary.one.ui.offers

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onehungary.one.domain.model.OffersEntity
import com.onehungary.one.domain.usecase.FetchOffersListUseCase
import com.onehungary.one.ui.offers.OfferListItemTitles.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersListViewModel @Inject constructor(
    private val fetchOffersListUseCase: FetchOffersListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _viewState = MutableStateFlow<OffersListViewState>(OffersListViewState.Empty)
    val viewState: StateFlow<OffersListViewState> = _viewState.asStateFlow()

    fun handle(action: OffersListViewAction) = when (action) {
        OffersListViewAction.Refresh -> fetchOffersList()
    }

    private fun fetchOffersList() = viewModelScope.launch(dispatcher) {
        _viewState.emit(OffersListViewState.Loading)
        try {
            val entities = fetchOffersListUseCase.invoke().first()
            val state = partitionOffersList(entities)
            _viewState.emit(state)
        } catch (e: Exception) {
            _viewState.emit(OffersListViewState.NetworkError)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private fun partitionOffersList(entities: List<OffersEntity>): OffersListViewState {
        if (entities.isEmpty()) return OffersListViewState.TryAgainLater

        val partition = entities.partition { it.isSpecial }
        val list: List<OfferListItem> = buildList {
            if (partition.first.isNotEmpty()) {
                add(OfferListItem.TitleListType(SPECIAL))
                addAll(partition.first.map { OfferListItem.OfferItem(it) })
            }
            if (partition.second.isNotEmpty()) {
                add(OfferListItem.TitleListType(OFFERS))
                addAll(partition.second.map { OfferListItem.OfferItem(it) })
            }
        }

        return OffersListViewState.Offers(list)
    }

}