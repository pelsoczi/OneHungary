package com.onehungary.one.api.services.details

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface DetailsService {

    @GET("FGPX")
    suspend fun details(): Response<ResponseBody>

}