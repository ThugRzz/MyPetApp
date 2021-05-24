package com.thugrzz.mypetapp.data.request

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("password") val password: String
)