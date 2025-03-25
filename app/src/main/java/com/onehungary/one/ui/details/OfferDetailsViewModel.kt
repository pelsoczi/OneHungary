package com.onehungary.one.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _viewState = MutableStateFlow<OfferDetailsViewState>(OfferDetailsViewState.Empty)
    val viewState: StateFlow<OfferDetailsViewState> = _viewState.asStateFlow()

    fun handle(action: OfferDetailsViewAction) = when (action) {
        OfferDetailsViewAction.RetrieveOfferDetails -> getOfferDetail(
            OfferDetailsFragmentArgs.fromSavedStateHandle(savedState).id
        )
    }

    private fun getOfferDetail(id: Int) = viewModelScope.launch(dispatcher) {

    }

}