package com.thugrzz.mypetapp.data.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String
)