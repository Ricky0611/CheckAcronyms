package com.example.checkacronyms.data

import com.google.gson.annotations.SerializedName

data class AcronymsResponse(

    @SerializedName("sf")
    var sf: String,

    @SerializedName("lfs")
    var lfs: List<LongForm>
)
