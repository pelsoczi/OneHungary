package com.onehungary.one.ui.offers

sealed class OffersListViewState {

    object Empty : OffersListViewState()

    object Loading: OffersListViewState()

    data class Offers(
        val entities: List<OfferListItem>
    ) : OffersListViewState()

    object TryAgainLater : OffersListViewState()

    object NetworkError : OffersListViewState()

}