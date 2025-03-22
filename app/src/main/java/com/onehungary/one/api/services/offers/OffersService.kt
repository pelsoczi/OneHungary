package com.onehungary.one.api.services.offers

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET


interface OffersService {

    @GET("K84Q")
    suspend fun offers(): Response<ResponseBody>

}
