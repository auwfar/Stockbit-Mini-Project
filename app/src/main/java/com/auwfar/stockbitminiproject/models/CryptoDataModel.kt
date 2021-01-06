package com.auwfar.stockbitminiproject.models

import com.google.gson.annotations.SerializedName

data class CryptoDataModel(
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfoModel?,
    @SerializedName("DISPLAY")
    val display: DisplayModel?
)