package com.onehungary.one.ui.offers

import com.onehungary.one.domain.model.OffersEntity

sealed class OfferListItem {

    data class TitleListType(
        val type: OfferListItemTitles
    ) : OfferListItem()

    data class OfferItem(
        val entity: OffersEntity
    ) : OfferListItem()

}

enum class OfferListItemTitles {
    SPECIAL,
    OFFERS
}