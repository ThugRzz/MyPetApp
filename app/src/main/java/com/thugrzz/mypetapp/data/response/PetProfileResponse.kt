package com.thugrzz.mypetapp.data.response

import com.google.gson.annotations.SerializedName
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex

data class PetProfileResponse(
    @SerializedName("pet_name") val petName: String,
    @SerializedName("pet_type") val petTypeId: Long,
    @SerializedName("breed_type") val breedId: Long,
    @SerializedName("sex") val sex: Sex?,
    @SerializedName("status") val status: PetStatus?,
    @SerializedName("height") val height: Double,
    @SerializedName("weight") val weight: Double
)