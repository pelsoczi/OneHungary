package com.onehungary.one.api.services.details

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onehungary.one.api.services.details.model.DetailsDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsDataSource @Inject constructor(
    private val detailsService: DetailsService,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun request() = flow<List<DetailsDTO>> {
        try {
            val response = detailsService.details()
            if (response.isSuccessful) {
                val jsonAsString = response.body()?.string() ?: ""
                val listType = object : TypeToken<List<DetailsDTO>>() {}.type
                val detailsList: List<DetailsDTO> = gson.fromJson(jsonAsString, listType)
                emit(detailsList)
                return@flow
            }
            throw RuntimeException("DetailsService response failed")
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(ioDispatcher)

}