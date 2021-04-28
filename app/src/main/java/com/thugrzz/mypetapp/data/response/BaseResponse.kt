package com.thugrzz.mypetapp.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<DATA>(
    @SerializedName("data") val data: DATA,
)