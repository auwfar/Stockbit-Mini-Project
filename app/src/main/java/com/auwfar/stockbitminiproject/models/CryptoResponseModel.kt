package com.auwfar.stockbitminiproject.models

import com.google.gson.annotations.SerializedName

data class CryptoResponseModel(
    @SerializedName("Data")
    val data: List<CryptoDataModel>?,
    @SerializedName("MetaData")
    val metaData: CryptoMetaDataModel?
)