package com.auwfar.stockbitminiproject.models

import com.google.gson.annotations.SerializedName

class RatingModel(
    @SerializedName("Weiss")
    val weiss: WeissModel?
)