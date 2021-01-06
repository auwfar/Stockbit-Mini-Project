package com.auwfar.stockbitminiproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.auwfar.stockbitminiproject.pagination.CryptoPagingSource
import com.auwfar.stockbitminiproject.repositories.CryptoRepository

class CryptoViewModel(private val cryptoRepository: CryptoRepository): ViewModel() {
    val cryptoState = Pager(PagingConfig(1)) {
        CryptoPagingSource(cryptoRepository)
    }.flow.cachedIn(viewModelScope)
}