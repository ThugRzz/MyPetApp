package com.thugrzz.mypetapp.data.request

import com.google.gson.annotations.SerializedName

data class UserProfileRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String
)
