package com.example.checkacronyms.data

import com.google.gson.annotations.SerializedName

data class Variation(

    @SerializedName("lf")
    var lf: String,

    @SerializedName("freq")
    var freq: Int,

    @SerializedName("since")
    var since: Int
)
