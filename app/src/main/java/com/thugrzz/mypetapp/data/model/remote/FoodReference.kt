package com.thugrzz.mypetapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class FoodReference(
    @SerializedName("ID") val id: Long,
    @SerializedName("breed_type") val breedType: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)