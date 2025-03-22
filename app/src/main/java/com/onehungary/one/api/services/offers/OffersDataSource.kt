package com.onehungary.one.api.services.offers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onehungary.one.api.services.offers.model.OffersDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class OffersDataSource @Inject constructor(
    private val offersService: OffersService,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun request() = flow<List<OffersDTO>> {
        try {
            val response = offersService.offers()
            if (response.isSuccessful) {
                val jsonAsString = response.body()?.string() ?: ""
                val listType = object : TypeToken<List<OffersDTO>>() {}.type
                val offersList: List<OffersDTO> = gson.fromJson(jsonAsString, listType)
                emit(offersList)
                return@flow
            }
            throw RuntimeException("OffersService response failed")
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(ioDispatcher)

}