package com.thugrzz.mypetapp.data.response

import com.google.gson.annotations.SerializedName

data class ImgResponse(
    @SerializedName("id") val id: String,
    @SerializedName("imgpath") val url: String
)