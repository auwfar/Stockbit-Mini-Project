package com.auwfar.stockbitminiproject.models

import com.auwfar.stockbitminiproject.R
import com.google.gson.annotations.SerializedName

data class CoinInfoModel(
    @SerializedName("Id")
    val id: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("FullName")
    val fullName: String?,
    @SerializedName("Rating")
    val rating: RatingModel?
) {
    fun getRating(): String? {
        return rating?.weiss?.rating
    }

    fun getRatingColor(): Int {
        return when {
            getRating()?.contains("A") == true -> R.color.forest_green
            getRating()?.contains("B") == true -> R.color.bright_sun
            else -> R.color.valencia
        }
    }
}