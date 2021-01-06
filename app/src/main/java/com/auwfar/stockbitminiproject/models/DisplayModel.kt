package com.auwfar.stockbitminiproject.models

import com.google.gson.annotations.SerializedName

data class DisplayModel(
    @SerializedName("USD")
    val usd: USDModel?
)

data class USDModel(
    @SerializedName("VOLUME24HOURTO")
    val volume24h: String?
)