package com.onehungary.one.api.services.details.model

import com.google.gson.annotations.SerializedName

data class DetailsDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("description") val description: String
)