package com.auwfar.stockbitminiproject.repositories

import com.auwfar.stockbitminiproject.api.CryptoApi
import com.auwfar.stockbitminiproject.models.CryptoMetaDataModel
import com.auwfar.stockbitminiproject.models.CryptoResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoRepository(private val api: CryptoApi) {
    suspend fun getCryptoTopTierVolume(page: Int, limit: Int): CryptoResponseModel? {
        return withContext(Dispatchers.IO) {
            try {
                api.getCryptoTopTierVolume(page, limit).execute().body()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}