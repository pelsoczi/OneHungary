package com.onehungary.one.domain.model

import com.onehungary.one.api.services.offers.model.OffersDTO

data class OffersEntity(
    val id: String,
    val rank: Int?,
    val isSpecial: Boolean,
    val name: String,
    val shortDescription: String
)

internal fun OffersDTO.asDomainEntity(): OffersEntity {
    return OffersEntity(id, rank, isSpecial, name, shortDescription)
}