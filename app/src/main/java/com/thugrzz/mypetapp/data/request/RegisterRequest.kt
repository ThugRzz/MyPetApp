package com.thugrzz.mypetapp.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("pet_name") val petName: String,
    @SerializedName("pet_age") val petAge: String,
    @SerializedName("pet") val petType: Int,
    @SerializedName("breed") val breed: Int
)