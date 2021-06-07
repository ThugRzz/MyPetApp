package com.thugrzz.mypetapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class Reference(
    @SerializedName("ID") val id: Long,
    @SerializedName("breed_type") val breedType: Long,
    @SerializedName("pet_type") val petType: Long,
    @SerializedName("title") val title: String,
    @SerializedName("step_title")val stepTitle:String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String
)