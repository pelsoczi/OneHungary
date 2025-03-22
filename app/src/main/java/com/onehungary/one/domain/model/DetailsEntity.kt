package com.onehungary.one.domain.model

import com.onehungary.one.api.services.details.model.DetailsDTO

data class DetailsEntity(
    val id: String,
    val name: String,
    val shortDescription: String,
    val description: String
)

fun DetailsDTO.asDomainEntity(): DetailsEntity {
    return DetailsEntity(id, name, shortDescription, description)
}