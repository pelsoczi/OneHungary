package com.onehungary.one.api.services.offers.model

import com.google.gson.annotations.SerializedName

data class OffersDTO(
    @SerializedName("id") val id: String,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("isSpecial") val isSpecial: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("shortDescription") val shortDescription: String
)