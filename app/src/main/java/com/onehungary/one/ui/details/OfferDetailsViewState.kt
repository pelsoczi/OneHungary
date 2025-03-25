package com.onehungary.one.ui.details

import com.onehungary.one.domain.model.DetailsEntity


sealed class OfferDetailsViewState {

    object Empty : OfferDetailsViewState()

    data class OfferDetail(
        val entity: DetailsEntity
    ) : OfferDetailsViewState()

    object NetworkError : OfferDetailsViewState()

    object TryAgainLater : OfferDetailsViewState()

}