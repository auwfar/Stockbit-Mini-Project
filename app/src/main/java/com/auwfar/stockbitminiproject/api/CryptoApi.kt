package com.auwfar.stockbitminiproject.api

import com.auwfar.stockbitminiproject.models.CryptoResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {
    @GET("/data/top/totaltoptiervolfull?tsym=USD")
    fun getCryptoTopTierVolume(@Query("page") page: Int, @Query("limit") limit: Int): Call<CryptoResponseModel>
}