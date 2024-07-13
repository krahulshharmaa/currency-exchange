package com.currency.flip.domain.model

import com.google.gson.annotations.SerializedName


data class Conversions(

    @SerializedName("name") var name: String = "",
    @SerializedName("rate") var rate: Double = 0.0

)