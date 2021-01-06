package com.auwfar.stockbitminiproject.pagination

import androidx.paging.PagingSource
import com.auwfar.stockbitminiproject.models.CoinInfoModel
import com.auwfar.stockbitminiproject.models.CryptoDataModel
import com.auwfar.stockbitminiproject.repositories.CryptoRepository
import java.io.IOException
import kotlin.math.ceil
import kotlin.math.roundToInt

class CryptoPagingSource(private val cryptoRepository: CryptoRepository): PagingSource<Int, CryptoDataModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoDataModel> {
        val page = params.key ?: 1
        val response = cryptoRepository.getCryptoTopTierVolume(page, CRYPTO_LIMIT)
        return if (response != null) {
            val totalCoin = response.metaData?.count ?: 0
            var maxPage = ceil((totalCoin / CRYPTO_LIMIT).toDouble()).roundToInt()
            if (totalCoin % CRYPTO_LIMIT != 0) maxPage++

            val data = response.data ?: listOf()
            val nextPage = if (page != maxPage) page + 1 else null
            LoadResult.Page(data, null, nextPage)
        } else {
            LoadResult.Error(IOException())
        }
    }

    companion object {
        private const val CRYPTO_LIMIT = 50
    }
}