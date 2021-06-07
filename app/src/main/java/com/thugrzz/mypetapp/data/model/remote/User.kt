package com.thugrzz.mypetapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("pet_name") val petName: String,
    @SerializedName("pet_age") val petAge: String,
    @SerializedName("pet_type") val petType: String,
    @SerializedName("pet_breed") val breedType: String,
)